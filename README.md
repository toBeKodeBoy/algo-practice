# Algo Practice — 每日算法练习记录

## 项目概述

日常算法刷题记录，基于 **Java 17 + Maven + JUnit 5**，结构按日期组织，搭配自动化统计脚本生成打卡日历与汇总分析。

## 项目结构

```
algo-practice/
├── .gitignore
├── pom.xml                          # Maven 项目
├── README.md                        # 项目总览 & 进度看板
├── ROUTINE.md                       # 地铁刷题每日节奏
├── pitfalls.md                      # 易错点库
├── scripts/
│   ├── stats.mjs                    # 统计脚本（打卡/热力图/进度）
│   └── review.mjs                   # 艾宾浩斯复习表
├── review/
│   ├── hot100.json                  # Hot 100 题单 & 阶段划分
│   ├── schedule.json                # 复习计划（自动生成）
│   └── README.md                    # 艾宾浩斯表格（自动生成）
├── template/
│   └── Solution.java                # 题解模板（含注释格式）
├── src/main/java/algo/
│   └── yyyy/                        # 年
│       └── MM/                      # 月
│           └── dd/                  # 日
│               ├── LC_xxx_Title.java        # 题解（附带注释元信息）
│               └── LC_xxx_TitleTest.java    # 单元测试
└── stats/
    ├── calendar.json                # 每日题数（统计脚本自动生成）
    ├── checkin.json                 # 每日打卡明细（JSON）
    ├── checkin.md                   # 每日打卡记录（Markdown）
    └── README.md                    # 统计面板（热力图 + 汇总表）
```

## 题解文件格式

每个题解文件头部包含元信息注释：

```java
// 题目  ...
// 示例  ...
// leetcode https://leetcode.cn/problems/...
// 算法核心 ...
// 时间 O(?) 空间 O(?)
// 难度 Easy / Medium / Hard
// 标签 数组, 哈希表, 双指针, ...
// 时段 早地铁 | 晚地铁
// 类型 新题 | 复习
```

**示例路径：** `src/main/java/algo/y2026/M06/d04/LC_0076_MinWindowSubstring.java`

## 统计与复习

| 命令 | 说明 |
|------|------|
| `node scripts/stats.mjs` | 打卡热力图、每日打卡记录、地铁时段统计、Hot 100 进度 |
| `node scripts/review.mjs today` | 今日艾宾浩斯到期复习 |
| `node scripts/review.mjs sync` | 从题解同步复习计划 |

每日节奏见 [ROUTINE.md](ROUTINE.md)，易错点见 [pitfalls.md](pitfalls.md)。

<!-- PROGRESS_START -->

## Hot 100 进度

| 指标 | 数值 |
|------|------|
| 总进度 | **14/100** (14%) |
| 当前阶段 | **P1** |

### 分阶段

| 阶段 | 专题 | 已完成 | 总数 | 进度 |
|:----:|------|:------:|:----:|:----:|
| P1 | 数组/哈希/双指针/二分/滑窗 | 14 | 25 | 56% |
| P2 | 链表 | 0 | 11 | 0% |
| P3 | 二叉树 | 0 | 14 | 0% |
| P4 | 栈队列/贪心/DP | 0 | 26 | 0% |
| P5 | 回溯/图/并查集/堆/位运算/数学 | 0 | 24 | 0% |

### P1 待做（优先）

- LC 80 删除有序数组中的重复项 II
- LC 167 两数之和 II
- LC 209 长度最小的子数组
- LC 35 搜索插入位置
- LC 74 搜索二维矩阵
- LC 34 在排序数组中查找元素的第一个和最后一个位置
- LC 33 搜索旋转排序数组
- LC 153 寻找旋转排序数组中的最小值
- LC 239 滑动窗口最大值
- LC 4 寻找两个正序数组的中位数
- LC 42 接雨水


<!-- PROGRESS_END -->

## 题解记录

<!-- SOLUTIONS_START -->

| 日期 | 题目 | 难度 | 标签 |
|------|------|:----:|------|
| 2026-06-06 | [LC_0049_GroupAnagrams](src/main/java/algo/y2026/M06/d06/LC_0049_GroupAnagrams.java) | Medium | - |
| 2026-06-04 | [LC_0003_LongestSubstringWithoutRepeating](src/main/java/algo/y2026/M06/d04/LC_0003_LongestSubstringWithoutRepeating.java) | Medium | 哈希表, 滑动窗口 |
| 2026-06-04 | [LC_0026_RemoveDuplicates](src/main/java/algo/y2026/M06/d04/LC_0026_RemoveDuplicates.java) | Easy | 数组, 快慢指针 |
| 2026-06-04 | [LC_0076_MinWindowSubstring](src/main/java/algo/y2026/M06/d04/LC_0076_MinWindowSubstring.java) | Medium | 滑动窗口 |
| 2026-06-03 | [LC_0041_FirstMissingPositive](src/main/java/algo/y2026/M06/d03/LC_0041_FirstMissingPositive.java) | Hard | 数组 |
| 2026-06-03 | [LC_0238_ProductExceptSelf](src/main/java/algo/y2026/M06/d03/LC_0238_ProductExceptSelf.java) | Medium | 数组, 前缀和 |
| 2026-06-02 | [LC_0056_MergeIntervals](src/main/java/algo/y2026/M06/d02/LC_0056_MergeIntervals.java) | Medium | 数组, 排序 |
| 2026-06-02 | [LC_0189_RotateArray](src/main/java/algo/y2026/M06/d02/LC_0189_RotateArray.java) | Medium | 数组 |
| 2026-06-01 | [LC_0053_MaximumSubarray](src/main/java/algo/y2026/M06/d01/LC_0053_MaximumSubarray.java) | Medium | 数组, 动态规划 |
| 2026-05-31 | [LC_0015_ThreeSum](src/main/java/algo/y2026/M05/d31/LC_0015_ThreeSum.java) | Medium | 数组, 双指针, 排序 |
| 2026-05-30 | [LC_0011_ContainerWithMostWater](src/main/java/algo/y2026/M05/d30/LC_0011_ContainerWithMostWater.java) | Medium | 数组, 双指针 |
| 2026-05-28 | [LC_0283_MoveZeroes](src/main/java/algo/y2026/M05/d28/LC_0283_MoveZeroes.java) | Easy | 数组, 双指针 |
| 2026-05-27 | [LC_0128_LongestConsecutiveSequence](src/main/java/algo/y2026/M05/d27/LC_0128_LongestConsecutiveSequence.java) | Medium | 数组, 哈希表 |
| 2026-05-18 | [LC_0001_TwoSum](src/main/java/algo/y2026/M05/d18/LC_0001_TwoSum.java) | Easy | 数组, 哈希表 |

<!-- SOLUTIONS_END -->

## 使用方式

```bash
# 1. 查看今日复习
node scripts/review.mjs today

# 2. 创建题解 (参考 template/Solution.java)
#    src/main/java/algo/y2026/M06/d06/LC_xxxx_Title.java

# 3. 运行测试验证
mvn test

# 4. 刷新统计 + 复习表
node scripts/stats.mjs
node scripts/review.mjs sync

# 5. 提交记录
git add .
git commit -m "feat: 2026-06-06 add Title solution"
```
