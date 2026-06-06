# 每日地铁刷题节奏

单程 **50 分钟**，早晚各一段。

## 时段分工

| 时段 | 时长 | 任务 | 题量 |
|------|:----:|------|:----:|
| 早地铁 | 50 min | 新题精做：15 min 独立思考 → 25 min 编码 → 10 min 复盘注释 | 1 题（Medium 为主） |
| 晚地铁 | 50 min | 艾宾浩斯到期复习 1~2 题 + 时间充裕时做 1 道 Easy 新题 | 1~2 复习 + 0~1 新题 |

## 每日流程

```bash
# 1. 早地铁前：查看今日复习清单
node scripts/review.mjs today

# 2. 早地铁：新题
#    复制 template/Solution.java → src/main/java/algo/y2026/M06/d06/LC_xxxx.java
#    注释标注：// 时段 早地铁  // 类型 新题

# 3. 晚地铁：复习到期题或 P1 Easy 补强
#    复习完成后：node scripts/review.mjs mark LC_0003 --round R4 --slot 晚地铁

# 4. 每日结束（生成打卡记录 stats/checkin.md）
mvn test
node scripts/stats.mjs
node scripts/review.mjs sync
```

## P1 剩余题单（优先顺序）

1. LC 49 字母异位词分组
2. LC 80 删除有序数组中的重复项 II
3. LC 167 两数之和 II
4. LC 209 长度最小的子数组
5. LC 35 搜索插入位置
6. LC 74 搜索二维矩阵
7. LC 34 查找元素第一个和最后一个位置
8. LC 33 搜索旋转排序数组
9. LC 153 寻找旋转排序数组中的最小值
10. LC 239 滑动窗口最大值
11. LC 42 接雨水
12. LC 4 寻找两个正序数组的中位数（Hard，放最后）

## 节奏约束

- 先想 15 min，不通再看题解
- 每日净新题 1~2 道，不贪多
- 错题标注：`思路错` / `边界错` / `API不熟`（见 [pitfalls.md](pitfalls.md)）
- 周末可选：周复盘 3 题限时抽检

## 目标

- **100 题**：约 58 个工作日（~12 周），目标 2026 年 8 月下旬
- P1 收尾后进入 **P2 链表**（11 题）
