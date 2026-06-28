# Algo Practice

LeetCode Hot 100 每日刷题：Java 题解 + JUnit + 艾宾浩斯复习 + 打卡统计。

**环境：** JDK 8+（推荐 17）· Maven 3.8+ · Node.js 18+

## 快速开始

```bash
npm install                              # 首次
mvn test                                 # Windows: .\scripts\mvn.ps1 test
node scripts/review.mjs daily            # 查看今日计划
```

**新题：** 复制 `template/Solution.java` → `src/main/java/algo/y{年}/M{月}/d{日}/LC_{题号}_{标题}.java`，编写 Test 后执行 sync。

**每日收尾：**

```bash
mvn test && node scripts/stats.mjs && node scripts/review.mjs sync
```

## 常用命令

```bash
node scripts/review.mjs daily                              # 今日新题 + 复习队列
node scripts/review.mjs review LC_0001 --slot 晚地铁       # 标记复习完成
node scripts/review.mjs sync                               # 同步复习排期
node scripts/stats.mjs                                     # 更新统计面板
npm run weekly                                             # 生成周计划（md + pdf）
```

## 目录说明

| 路径 | 说明 |
|------|------|
| `src/main/java/algo/y*/M*/d*/` | 按日期存放题解 |
| `template/Solution.java` | 新题模板 |
| `scripts/` | review / stats / weekly 脚本 |
| `review/` | Hot 100 题单、复习计划 |
| `stats/` | 打卡统计、**完整题解列表** |
| `docs/` | 刷题节奏、易错点 |

## 更多文档

- [docs/ROUTINE.md](docs/ROUTINE.md) — 每日地铁刷题节奏
- [docs/pitfalls.md](docs/pitfalls.md) — 易错点库
- [stats/README.md](stats/README.md) — 题解列表与打卡热力图
- [review/README.md](review/README.md) — 艾宾浩斯复习表
- [CLAUDE.md](CLAUDE.md) — 题解规范与 AI 协作约定

<!-- PROGRESS_START -->

## Hot 100 进度

| 指标 | 数值 |
|------|------|
| 总进度 | **42/100** (42%) |
| 当前阶段 | **P3** |

### 分阶段

| 阶段 | 专题 | 已完成 | 总数 | 进度 |
|:----:|------|:------:|:----:|:----:|
| P1 | 数组/哈希/双指针/二分/滑窗 | 25 | 25 | 100% |
| P2 | 链表 | 11 | 11 | 100% |
| P3 | 二叉树 | 6 | 14 | 43% |
| P4 | 栈队列/贪心/DP | 0 | 26 | 0% |
| P5 | 回溯/图/并查集/堆/位运算/数学 | 0 | 24 | 0% |


<!-- PROGRESS_END -->
