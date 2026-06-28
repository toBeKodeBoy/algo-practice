/**
 * generate-detailed-weekly.mjs
 *
 * 生成详尽的本周算法学习计划（含题目描述、算法核心、示例流程、复杂度、代码实现）
 * 输出：review/weekly/2026-06-22-周计划算法.{md,pdf}
 *
 * 用法：node scripts/generate-detailed-weekly.mjs [--monday YYYY-MM-DD]
 */

import { readFileSync, writeFileSync, mkdirSync, existsSync, readdirSync, statSync } from 'fs';
import { join, dirname } from 'path';
import { marked } from 'marked';
import { chromium } from 'playwright';

// ──────────────────────────────────────────────
// Constants
// ──────────────────────────────────────────────
const ROOT = join(import.meta.dirname, '..');
const REVIEW_DIR = join(ROOT, 'review');
const SCHEDULE_PATH = join(REVIEW_DIR, 'schedule.json');
const HOT100_PATH = join(REVIEW_DIR, 'hot100.json');
const OUT_DIR = join(REVIEW_DIR, 'weekly');
const SRC_BASE = join(ROOT, 'src', 'main', 'java', 'algo');

const WEEKDAY_ZH = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];

// ──────────────────────────────────────────────
// Helpers
// ──────────────────────────────────────────────
function pad2(n) {
  return String(n).padStart(2, '0');
}

function isoDateLocal(d) {
  const y = d.getFullYear();
  const m = pad2(d.getMonth() + 1);
  const day = pad2(d.getDate());
  return `${y}-${m}-${day}`;
}

function addDays(dateStr, days) {
  const d = new Date(dateStr + 'T12:00:00');
  d.setDate(d.getDate() + days);
  return isoDateLocal(d);
}

function getMonday(w) {
  const d = new Date(w + 'T12:00:00');
  const day = d.getDay();
  const diff = (day + 6) % 7;
  d.setDate(d.getDate() - diff);
  return isoDateLocal(d);
}

function weekdayZh(dateStr) {
  const d = new Date(dateStr + 'T12:00:00');
  return WEEKDAY_ZH[d.getDay()];
}

function loadJson(path) {
  return JSON.parse(readFileSync(path, 'utf-8'));
}

function lcIdFromNum(num) {
  return `LC_${String(num).padStart(4, '0')}`;
}

function parseArgs(argv) {
  const args = { _: [] };
  for (let i = 0; i < argv.length; i++) {
    const a = argv[i];
    if (a.startsWith('--')) {
      const key = a.slice(2);
      const val = argv[i + 1] && !argv[i + 1].startsWith('--') ? argv[++i] : true;
      args[key] = val;
    } else {
      args._.push(a);
    }
  }
  return args;
}

// ──────────────────────────────────────────────
// Source-file scanning & parsing
// ──────────────────────────────────────────────

/** Scan src/main/java/algo/y2026/** for existing LC source files */
function buildSourceMap() {
  const map = new Map(); // lcId -> { meta, code }

  function walk(dir) {
    let entries;
    try {
      entries = readdirSync(dir);
    } catch {
      return;
    }
    for (const e of entries) {
      const full = join(dir, e);
      let stat;
      try {
        stat = statSync(full);
      } catch {
        continue;
      }
      if (stat.isDirectory()) {
        walk(full);
      } else if (e.endsWith('.java') && e.startsWith('LC_')) {
        const lcId = e.split('_').slice(0, 2).join('_'); // "LC_0001"
        const content = readFileSync(full, 'utf-8');
        map.set(lcId, parseJavaFile(content));
      }
    }
  }

  walk(join(SRC_BASE, 'y2026'));
  return map;
}

function parseJavaFile(content) {
  // Extract metadata from // comments
  const meta = {
    title: '',
    example: '',
    leetcode: '',
    core: '',
    timeComplexity: '',
    spaceComplexity: '',
    difficulty: '',
    tags: '',
    slot: '',
    type: '',
  };

  const lines = content.split('\n');
  const codeLines = [];
  let inClass = false;
  let braceDepth = 0;
  let classStarted = false;

  for (const line of lines) {
    const trimmed = line.trim();

    // Parse metadata comments
    if (trimmed.startsWith('// 题目 ')) meta.title = trimmed.slice(5).trim();
    else if (trimmed.startsWith('// 示例 ')) meta.example = trimmed.slice(5).trim();
    else if (trimmed.startsWith('// leetcode ')) meta.leetcode = trimmed.slice(11).trim();
    else if (trimmed.startsWith('// 算法核心 ')) meta.core = trimmed.slice(7).trim();
    else if (trimmed.startsWith('// 时间 ')) {
      const parts = trimmed.slice(4).trim().split(/\s+/);
      for (let i = 0; i < parts.length; i++) {
        if (parts[i] === '时间') meta.timeComplexity = parts[i + 1] || '';
        if (parts[i] === '空间') meta.spaceComplexity = parts[i + 1] || '';
      }
      // Fallback: parse "O(n) 空间 O(1)" style
      if (!meta.timeComplexity || !meta.spaceComplexity) {
        const tMatch = trimmed.match(/时间\s+(O\([^)]+\))/);
        const sMatch = trimmed.match(/空间\s+(O\([^)]+\))/);
        if (tMatch) meta.timeComplexity = tMatch[1];
        if (sMatch) meta.spaceComplexity = sMatch[1];
      }
    } else if (trimmed.startsWith('// 难度 ')) meta.difficulty = trimmed.slice(5).trim();
    else if (trimmed.startsWith('// 标签 ')) meta.tags = trimmed.slice(5).trim();
    else if (trimmed.startsWith('// 时段 ')) meta.slot = trimmed.slice(5).trim();
    else if (trimmed.startsWith('// 类型 ')) meta.type = trimmed.slice(5).trim();

    // Extract code: from "public class" line to closing brace of class
    if (trimmed.startsWith('public class ') && trimmed.includes('{')) {
      inClass = true;
      classStarted = true;
      braceDepth = 1;
      codeLines.push(line);
    } else if (inClass) {
      codeLines.push(line);
      for (const ch of line) {
        if (ch === '{') braceDepth++;
        if (ch === '}') braceDepth--;
      }
      if (braceDepth <= 0 && classStarted) {
        inClass = false;
      }
    }
  }

  return { meta, code: codeLines.join('\n') };
}

// ──────────────────────────────────────────────
// New-problem definitions (no source code yet)
// ──────────────────────────────────────────────

function defineNewProblems() {
  return {
    'LC_0021': {
      title: '合并两个有序链表',
      example: 'list1=[1,2,4], list2=[1,3,4] => [1,1,2,3,4,4]',
      leetcode: 'https://leetcode.cn/problems/merge-two-sorted-lists/',
      core: '迭代法：dummy 头节点 + 双指针遍历两链表，取较小值接入结果链',
      timeComplexity: 'O(n+m)',
      spaceComplexity: 'O(1)',
      difficulty: 'Easy',
      tags: '链表, 递归',
      code: `public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;

    while (list1 != null && list2 != null) {
        if (list1.val <= list2.val) {
            curr.next = list1;
            list1 = list1.next;
        } else {
            curr.next = list2;
            list2 = list2.next;
        }
        curr = curr.next;
    }
    curr.next = (list1 != null) ? list1 : list2;
    return dummy.next;
}`
    },
    'LC_0023': {
      title: '合并 K 个升序链表',
      example: 'lists=[[1,4,5],[1,3,4],[2,6]] => [1,1,2,3,4,4,5,6]',
      leetcode: 'https://leetcode.cn/problems/merge-k-sorted-lists/',
      core: '优先队列（小顶堆）：将 K 个头节点入堆，每次弹出最小节点接入结果链，并将其 next 入堆',
      timeComplexity: 'O(n log K)',
      spaceComplexity: 'O(K)',
      difficulty: 'Hard',
      tags: '链表, 分治, 堆',
      code: `public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    PriorityQueue<ListNode> pq = new PriorityQueue<>(
        (a, b) -> a.val - b.val);

    for (ListNode node : lists) {
        if (node != null) pq.offer(node);
    }

    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    while (!pq.isEmpty()) {
        ListNode node = pq.poll();
        curr.next = node;
        curr = curr.next;
        if (node.next != null) pq.offer(node.next);
    }
    return dummy.next;
}`
    },
    'LC_0148': {
      title: '排序链表',
      example: 'head=[4,2,1,3] => [1,2,3,4]',
      leetcode: 'https://leetcode.cn/problems/sort-list/',
      core: '归并排序（自底向上）：快慢指针找中点切分，递归排序后合并，满足 O(1) 空间要求',
      timeComplexity: 'O(n log n)',
      spaceComplexity: 'O(log n)',
      difficulty: 'Medium',
      tags: '链表, 排序, 归并',
      code: `public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) return head;

    // 快慢指针找中点
    ListNode slow = head, fast = head.next;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    ListNode mid = slow.next;
    slow.next = null;

    ListNode left = sortList(head);
    ListNode right = sortList(mid);
    return merge(left, right);
}

private ListNode merge(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0), curr = dummy;
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            curr.next = l1; l1 = l1.next;
        } else {
            curr.next = l2; l2 = l2.next;
        }
        curr = curr.next;
    }
    curr.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}`
    },
    'LC_0025': {
      title: 'K 个一组翻转链表',
      example: 'head=[1,2,3,4,5], k=2 => [2,1,4,3,5]',
      leetcode: 'https://leetcode.cn/problems/reverse-nodes-in-k-group/',
      core: '分组翻转：dummy 头节点，每次定位 K 个节点的区间，区间内翻转后接回，不足 K 不翻转',
      timeComplexity: 'O(n)',
      spaceComplexity: 'O(1)',
      difficulty: 'Hard',
      tags: '链表, 翻转',
      code: `public ListNode reverseKGroup(ListNode head, int k) {
    ListNode dummy = new ListNode(0, head);
    ListNode prev = dummy;

    while (true) {
        // 定位第 K 个节点
        ListNode end = prev;
        for (int i = 0; i < k && end != null; i++) {
            end = end.next;
        }
        if (end == null) break;

        ListNode start = prev.next;
        ListNode next = end.next;

        // 翻转 [start, end]
        ListNode curr = start, prevNode = next;
        while (curr != next) {
            ListNode tmp = curr.next;
            curr.next = prevNode;
            prevNode = curr;
            curr = tmp;
        }

        prev.next = end;
        prev = start;
    }
    return dummy.next;
}`
    },
    'LC_0094': {
      title: '二叉树的中序遍历',
      example: 'root=[1,null,2,3] => [1,3,2]',
      leetcode: 'https://leetcode.cn/problems/binary-tree-inorder-traversal/',
      core: '递归：左→根→右；迭代：栈模拟，先深入左子树，出栈访问，进入右子树',
      timeComplexity: 'O(n)',
      spaceComplexity: 'O(n)',
      difficulty: 'Easy',
      tags: '二叉树, 遍历, 栈',
      code: `// 递归
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    inorder(root, res);
    return res;
}
private void inorder(TreeNode node, List<Integer> res) {
    if (node == null) return;
    inorder(node.left, res);
    res.add(node.val);
    inorder(node.right, res);
}

// 迭代（栈）
public List<Integer> inorderTraversalIter(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        res.add(curr.val);
        curr = curr.right;
    }
    return res;
}`
    },
    'LC_0104': {
      title: '二叉树的最大深度',
      example: 'root=[3,9,20,null,null,15,7] => 3',
      leetcode: 'https://leetcode.cn/problems/maximum-depth-of-binary-tree/',
      core: '递归：max(左子树深度, 右子树深度) + 1；层序遍历：BFS 统计层数',
      timeComplexity: 'O(n)',
      spaceComplexity: 'O(n)',
      difficulty: 'Easy',
      tags: '二叉树, DFS, BFS',
      code: `// 递归
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
}

// BFS（层序遍历）
public int maxDepthBFS(TreeNode root) {
    if (root == null) return 0;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    int depth = 0;
    while (!q.isEmpty()) {
        int size = q.size();
        depth++;
        for (int i = 0; i < size; i++) {
            TreeNode node = q.poll();
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
    }
    return depth;
}`
    },
    'LC_0226': {
      title: '翻转二叉树',
      example: 'root=[4,2,7,1,3,6,9] => [4,7,2,9,6,3,1]',
      leetcode: 'https://leetcode.cn/problems/invert-binary-tree/',
      core: '递归：交换左右子树，递归翻转左右子节点；也可用 BFS 层序遍历交换',
      timeComplexity: 'O(n)',
      spaceComplexity: 'O(n)',
      difficulty: 'Easy',
      tags: '二叉树, DFS',
      code: `// 递归
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode left = invertTree(root.right);
    TreeNode right = invertTree(root.left);
    root.left = left;
    root.right = right;
    return root;
}`
    },
    'LC_0101': {
      title: '对称二叉树',
      example: 'root=[1,2,2,3,4,4,3] => true',
      leetcode: 'https://leetcode.cn/problems/symmetric-tree/',
      core: '递归：判断左右子树是否镜像对称（左.left vs 右.right && 左.right vs 右.left）',
      timeComplexity: 'O(n)',
      spaceComplexity: 'O(n)',
      difficulty: 'Easy',
      tags: '二叉树, DFS, BFS',
      code: `// 递归
public boolean isSymmetric(TreeNode root) {
    if (root == null) return true;
    return isMirror(root.left, root.right);
}
private boolean isMirror(TreeNode t1, TreeNode t2) {
    if (t1 == null && t2 == null) return true;
    if (t1 == null || t2 == null) return false;
    return t1.val == t2.val
        && isMirror(t1.left, t2.right)
        && isMirror(t1.right, t2.left);
}`
    },
    'LC_0102': {
      title: '二叉树的层序遍历',
      example: 'root=[3,9,20,null,null,15,7] => [[3],[9,20],[15,7]]',
      leetcode: 'https://leetcode.cn/problems/binary-tree-level-order-traversal/',
      core: 'BFS（队列）：每轮处理当前层所有节点，收集值并添加下一层节点',
      timeComplexity: 'O(n)',
      spaceComplexity: 'O(n)',
      difficulty: 'Medium',
      tags: '二叉树, BFS, 队列',
      code: `public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;

    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    while (!q.isEmpty()) {
        int size = q.size();
        List<Integer> level = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode node = q.poll();
            level.add(node.val);
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        res.add(level);
    }
    return res;
}`
    },
    'LC_0105': {
      title: '从前序与中序遍历序列构造二叉树',
      example: 'preorder=[3,9,20,15,7], inorder=[9,3,15,20,7] => 二叉树 [3,9,20,null,null,15,7]',
      leetcode: 'https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/',
      core: '前序第一个为根，在中序中找到根的位置，左半为左子树，右半为右子树，递归构建',
      timeComplexity: 'O(n)',
      spaceComplexity: 'O(n)',
      difficulty: 'Medium',
      tags: '二叉树, 分治, 哈希表',
      code: `public TreeNode buildTree(int[] preorder, int[] inorder) {
    Map<Integer, Integer> idxMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
        idxMap.put(inorder[i], i);
    }
    return build(preorder, 0, preorder.length - 1,
                 inorder, 0, inorder.length - 1, idxMap);
}

private TreeNode build(int[] pre, int pL, int pR,
                       int[] in, int iL, int iR,
                       Map<Integer, Integer> map) {
    if (pL > pR || iL > iR) return null;

    int rootVal = pre[pL];
    TreeNode root = new TreeNode(rootVal);
    int rootIdx = map.get(rootVal);
    int leftSize = rootIdx - iL;

    root.left = build(pre, pL + 1, pL + leftSize,
                      in, iL, rootIdx - 1, map);
    root.right = build(pre, pL + leftSize + 1, pR,
                       in, rootIdx + 1, iR, map);
    return root;
}`
    }
  };
}

// ──────────────────────────────────────────────
// Review-problem content (from existing source)
// ──────────────────────────────────────────────

function buildReviewContent(sourceMap) {
  const need = ['LC_0019', 'LC_0160', 'LC_0092', 'LC_0142', 'LC_0024',
                'LC_0056', 'LC_0002', 'LC_0206'];

  const result = {};
  for (const lcId of need) {
    const entry = sourceMap.get(lcId);
    if (entry) {
      result[lcId] = entry;
    }
  }
  return result;
}

// ──────────────────────────────────────────────
// Weekly schedule definition
// ──────────────────────────────────────────────

function buildWeeklyPlan(mondayStr) {
  const weekdays = [0, 1, 2, 3, 4].map(i => addDays(mondayStr, i));
  const friday = weekdays[4];

  // New problems schedule
  const newSchedule = [
    { date: weekdays[0], problems: ['LC_0021', 'LC_0023'] },
    { date: weekdays[1], problems: ['LC_0148', 'LC_0025'] },
    { date: weekdays[2], problems: ['LC_0094', 'LC_0104'] },
    { date: weekdays[3], problems: ['LC_0226', 'LC_0101'] },
    { date: weekdays[4], problems: ['LC_0102', 'LC_0105'] },
  ];

  // Review schedule (2 per day, from due+overdue)
  const reviewSchedule = [
    { date: weekdays[0], problems: [
      { lcId: 'LC_0019', round: 'R3', due: '2026-06-22' },
      { lcId: 'LC_0160', round: 'R2', due: '2026-06-22' },
    ]},
    { date: weekdays[1], problems: [
      { lcId: 'LC_0092', round: 'R4', due: '2026-06-23' },
      { lcId: 'LC_0142', round: 'R3', due: '2026-06-23' },
    ]},
    { date: weekdays[2], problems: [
      { lcId: 'LC_0024', round: 'R4', due: '2026-06-24' },
      { lcId: 'LC_0056', round: 'R5', due: '2026-06-24' },
    ]},
    { date: weekdays[3], problems: [
      { lcId: 'LC_0019', round: 'R4', due: '2026-06-25' },
      { lcId: 'LC_0002', round: 'R3', due: '2026-06-25' },
    ]},
    { date: weekdays[4], problems: [
      { lcId: 'LC_0142', round: 'R4', due: '2026-06-26' },
      { lcId: 'LC_0206', round: 'R4', due: '2026-06-19' },
    ]},
  ];

  return { monday: mondayStr, friday, weekdays, newSchedule, reviewSchedule };
}

// ──────────────────────────────────────────────
// Markdown generation helper
// ──────────────────────────────────────────────

function escapeCode(code) {
  // Ensure proper indentation and no trailing issues
  return code.trim();
}

function formatAlgorithmCard(title, meta, code, roundLabel) {
  let md = '';
  const lcId = meta.lcId || '';
  const roundTag = roundLabel ? ` (${roundLabel})` : '';

  md += `### ${title}${roundTag}\n\n`;

  // 题目描述
  md += `**题目描述：** ${meta.title}\n\n`;

  // 示例
  if (meta.example) {
    md += `**示例：** \`${meta.example}\`\n\n`;
  }

  // LeetCode 链接
  if (meta.leetcode) {
    md += `**LeetCode：** [${meta.leetcode}](${meta.leetcode})\n\n`;
  }

  // 算法核心
  md += `**算法核心：** ${meta.core}\n\n`;

  // 复杂度
  md += `**复杂度：** 时间 ${meta.timeComplexity}，空间 ${meta.spaceComplexity}\n\n`;

  // 标签
  if (meta.tags) {
    md += `**标签：** \`${meta.tags}\`\n\n`;
  }

  // 代码实现
  if (code) {
    md += `**代码实现：**\n\n\`\`\`java\n${escapeCode(code)}\n\`\`\`\n\n`;
  }

  return md;
}

// ──────────────────────────────────────────────
// Build the full markdown document
// ──────────────────────────────────────────────

function buildDetailedMarkdown(plan, newDefs, reviewContent, schedule) {
  const { monday, friday, weekdays, newSchedule, reviewSchedule } = plan;
  const generatedAt = isoDateLocal(new Date());

  let md = `# 周计划算法 ${monday} ~ ${friday}\n\n`;
  md += `> 自动生成于 ${generatedAt} | 速刷 Hot 100\n\n`;
  md += `---\n\n`;

  // ── Daily sections ──
  for (let i = 0; i < weekdays.length; i++) {
    const date = weekdays[i];
    const w = weekdayZh(date);

    md += `## ${date} ${w}\n\n`;
    md += `---\n\n`;

    // ── 新题 ──
    md += `### 📖 新题（学习）\n\n`;
    const dayNew = newSchedule[i].problems;
    for (const lcId of dayNew) {
      const def = newDefs[lcId];
      if (!def) continue;
      const titleNo = lcId.replace('LC_', 'LC ');
      const title = `${titleNo} ${def.title}`;
      md += formatAlgorithmCard(title, { ...def, lcId, leetcode: def.leetcode || `https://leetcode.cn/problems/` },
                                def.code, null);
    }

    // ── 复习 ──
    md += `### 🔄 复习\n\n`;
    const dayReview = reviewSchedule[i].problems;
    for (const r of dayReview) {
      const entry = reviewContent[r.lcId];
      if (!entry) continue;
      const { meta, code } = entry;
      const titleNo = r.lcId.replace('LC_', 'LC ');
      const title = `${titleNo} ${meta.title}`;
      md += formatAlgorithmCard(title, { ...meta, lcId: r.lcId, leetcode: `https://leetcode.cn/problems/` },
                                code, r.round);
    }

    md += `---\n\n`;
  }

  // ── 本周汇总 ──
  md += `## 📋 本周算法汇总\n\n`;
  md += `> **学习节奏：** 每日 2 道新题 + 2 道复盘\n\n`;

  // 新学汇总
  md += `### 新学算法（10道）\n\n`;
  md += `| 题号 | 题目 | 难度 | 计划日期 |\n`;
  md += `|------|------|:----:|:--------:|\n`;
  for (const day of newSchedule) {
    for (const lcId of day.problems) {
      const def = newDefs[lcId];
      if (!def) continue;
      md += `| ${lcId} | ${def.title} | ${def.difficulty} | ${day.date} ${weekdayZh(day.date)} |\n`;
    }
  }
  md += `\n`;

  // 复习汇总
  md += `### 复习算法（10道）\n\n`;
  md += `| 题号 | 题目 | 轮次 | 计划日期 |\n`;
  md += `|------|------|:----:|:--------:|\n`;
  for (const day of reviewSchedule) {
    for (const r of day.problems) {
      const entry = reviewContent[r.lcId];
      if (!entry) continue;
      md += `| ${r.lcId} | ${entry.meta.title} | ${r.round} | ${day.date} ${weekdayZh(day.date)} |\n`;
    }
  }
  md += `\n`;

  // 统计汇总
  const allNewLcIds = newSchedule.flatMap(d => d.problems);
  const allReview = reviewSchedule.flatMap(d => d.problems);
  const reviewLcIds = [...new Set(allReview.map(r => r.lcId))];

  // 按题型分类统计
  const newByPhase = {};
  for (const lcId of allNewLcIds) {
    const def = newDefs[lcId];
    const phase = def && def.difficulty === 'Easy' ? 'P3 二叉树入门'
                : (lcId.startsWith('LC_002') ? 'P2 链表收尾' : 'P3 二叉树');
    if (!newByPhase[phase]) newByPhase[phase] = [];
    newByPhase[phase].push(lcId);
  }

  md += `### 进度一览\n\n`;
  md += `| 维度 | 数值 |\n`;
  md += `|------|:----:|\n`;
  md += `| 📅 周期 | ${monday} ~ ${friday} |\n`;
  md += `| 📖 新题总量 | ${allNewLcIds.length} 道 |\n`;
  md += `| 🔄 复习总量 | ${allReview.length} 道 |\n`;
  md += `| 🎯 每日节奏 | 2 新 + 2 复 |\n`;
  md += `| 📚 本周涉及题型 | P2 链表收尾 → P3 二叉树起步 |\n`;
  md += `\n`;

  md += `### 本周围绕题型\n\n`;
  for (const [phase, lcs] of Object.entries(newByPhase)) {
    md += `- **${phase}**：${lcs.join('、')}\n`;
  }
  md += `\n`;

  md += `> 💡 **建议：** 早地铁学习新题，晚地铁复盘，每周末回顾本周所有题目\n\n`;
  md += `> 由 \`scripts/generate-detailed-weekly.mjs\` 自动生成\n`;

  return md;
}

// ──────────────────────────────────────────────
// HTML + PDF rendering
// ──────────────────────────────────────────────

function markdownToHtml(md, { title }) {
  const body = marked.parse(md);
  const css = `
    @page { size: A4; margin: 16mm 14mm; }
    body { font-family: "Microsoft YaHei", "Noto Sans CJK SC", "PingFang SC", Arial, sans-serif; color: #111; line-height: 1.6; }
    h1 { font-size: 20pt; margin: 0 0 8pt; color: #1a1a2e; }
    h2 { font-size: 14pt; margin: 20pt 0 8pt; color: #16213e; border-bottom: 2px solid #0f3460; padding-bottom: 4pt; }
    h3 { font-size: 12pt; margin: 14pt 0 6pt; color: #0f3460; }
    h4 { font-size: 11pt; margin: 10pt 0 4pt; color: #333; }
    p { font-size: 10.5pt; margin: 6pt 0; }
    blockquote { margin: 8pt 0; padding: 6pt 10pt; border-left: 3pt solid #0f3460; color: #555; background: #f9f9fb; }
    table { width: 100%; border-collapse: collapse; margin: 8pt 0; }
    th, td { border: 1px solid #d0d0d0; padding: 5pt 7pt; vertical-align: top; font-size: 10pt; }
    th { background: #0f3460; color: #fff; font-weight: 600; }
    tr:nth-child(even) { background: #f8f8fc; }
    code { font-family: "Cascadia Mono", "JetBrains Mono", Consolas, monospace; font-size: 9pt; background: #f0f0f5; padding: 1pt 3pt; border-radius: 3pt; }
    pre { background: #1a1a2e; color: #e0e0e0; padding: 10pt 12pt; border-radius: 5pt; overflow-x: auto; font-size: 9pt; line-height: 1.4; }
    pre code { background: transparent; padding: 0; color: inherit; font-size: 9pt; }
    hr { border: none; border-top: 1pt solid #e0e0e0; margin: 14pt 0; }
    strong { color: #1a1a2e; }
  `;

  return `<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>${title}</title>
  <style>${css}</style>
</head>
<body>
${body}
</body>
</html>`;
}

async function renderPdf(html, outPdfPath) {
  const browser = await chromium.launch();
  try {
    const page = await browser.newPage();
    await page.setContent(html, { waitUntil: 'networkidle' });
    await page.pdf({
      path: outPdfPath,
      format: 'A4',
      printBackground: true,
      margin: { top: '14mm', bottom: '14mm', left: '12mm', right: '12mm' },
    });
  } finally {
    await browser.close();
  }
}

// ──────────────────────────────────────────────
// Main
// ──────────────────────────────────────────────

async function main() {
  const args = parseArgs(process.argv.slice(2));
  const monday = args.monday || '2026-06-22';

  console.log(`📅 Generating detailed weekly plan for ${monday} ~ ${addDays(monday, 4)}`);

  // 1. Load data
  const schedule = loadJson(SCHEDULE_PATH);
  const hot100 = loadJson(HOT100_PATH);

  // 2. Scan existing source files
  console.log('🔍 Scanning source files...');
  const sourceMap = buildSourceMap();
  console.log(`   Found ${sourceMap.size} existing solutions`);

  // 3. Build plan
  const plan = buildWeeklyPlan(monday);

  // 4. Build content
  const newDefs = defineNewProblems();
  const reviewContent = buildReviewContent(sourceMap);

  console.log(`   New problems: ${Object.keys(newDefs).length}`);
  console.log(`   Review problems with source: ${Object.keys(reviewContent).length}`);

  // 5. Generate markdown
  console.log('📝 Generating markdown...');
  const md = buildDetailedMarkdown(plan, newDefs, reviewContent, schedule);
  const title = `周计划算法 ${plan.monday} ~ ${plan.friday}`;

  // 6. Output
  mkdirSync(OUT_DIR, { recursive: true });
  const outBase = `2026-06-22-周计划算法`;
  const outMdPath = join(OUT_DIR, `${outBase}.md`);
  const outPdfPath = join(OUT_DIR, `${outBase}.pdf`);

  writeFileSync(outMdPath, md, 'utf-8');
  console.log(`✅ Saved ${outMdPath}`);

  // 7. Render PDF
  console.log('📄 Rendering PDF via Playwright...');
  const html = markdownToHtml(md, { title });
  await renderPdf(html, outPdfPath);
  console.log(`✅ Saved ${outPdfPath}`);
  console.log('🎉 Done!');
}

main().catch((err) => {
  console.error('❌ Error:', err);
  process.exit(1);
});
