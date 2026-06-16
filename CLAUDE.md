# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Daily LeetCode Hot 100 algorithm practice in **Java 17 + Maven + JUnit 5**, organized by date with Ebbinghaus spaced repetition review and automated statistics.

Progress: 25/100 (P1 数组/哈希/双指针/二分/滑窗 100% done, entering P2 链表).

## Build & Test

```bash
# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=LC_0033_SearchInRotatedSortedArrayTest

# On Windows with custom JAVA_HOME
.\scripts\mvn.ps1 test
```

## Key Scripts

```bash
# View today's review schedule
node scripts/review.mjs today

# Full daily plan (recommended new problem + review queue)
node scripts/review.mjs daily

# Mark a review round as done
node scripts/review.mjs review LC_xxxx --slot 晚地铁

# Scan solutions and sync review schedule
node scripts/review.mjs sync

# Add a new problem to review schedule
node scripts/review.mjs add LC_xxxx --date YYYY-MM-DD --title "..."

# Regenerate stats (calendar, checkin, progress, heatmap)
node scripts/stats.mjs
```

## Project Structure

```
├── src/main/java/algo/y{year}/M{month}/d{day}/
│   ├── LC_{xxxx}_{Title}.java          # 题解（含元信息注释）
│   └── LC_{xxxx}_{Title}Test.java      # JUnit 5 单元测试
├── scripts/
│   ├── stats.mjs                       # 统计脚本（打卡/热力图/进度）
│   └── review.mjs                      # 艾宾浩斯复习管理
├── review/
│   ├── hot100.json                     # Hot 100 题单 & 5阶段划分
│   ├── schedule.json                   # 复习计划（自动生成）
│   └── README.md                       # 复习表（自动生成）
├── template/Solution.java              # 新题解模板
├── docs/
│   ├── ROUTINE.md                      # 每日刷题节奏
│   └── pitfalls.md                     # 易错点库
└── stats/
    ├── README.md                       # 统计面板（自动生成）
    ├── checkin.md                      # 每日打卡记录
    ├── checkin.json / calendar.json    # 统计数据
```

## Coding Conventions

### File Layout
- **Package:** `algo.y{year}.M{month}.d{day}` (matches directory structure)
- **Public class:** `LC_{pad4}_{CamelCaseTitle}` — one public class per file
- **Test class:** same name + `Test` suffix, package-private
- **No nested classes** in solutions

### Header Metadata
Every solution file starts with Chinese comment metadata:
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
// 易错 思路错 | 边界错 | API不熟  (optional)
```

### Solution Pattern
- Static methods or instance methods, no constructor injection needed
- ALWAYS null-check and empty-check input (`nums == null || nums.length == 0`)
- No external dependencies beyond standard library
- Include `main()` for quick standalone testing (optional)

### Test Pattern
- JUnit 5 (`import org.junit.jupiter.api.Test`)
- `import static org.junit.jupiter.api.Assertions.*`
- Each `@Test` method covers one distinct scenario (example, edge case, null/empty)
- Test class field holds a fresh `solution` instance
- Method naming: `testScenarioName()` or descriptive Chinese-style

## Daily Workflow

1. **早地铁**: `node scripts/review.mjs daily` → pick recommended new problem → copy template → implement
2. **晚地铁**: review due problems → `node scripts/review.mjs review LC_xxxx --slot 晚地铁`
3. **End of day**: `mvn test && node scripts/stats.mjs && node scripts/review.mjs sync`

## Key Constraints

- Ebbinghaus intervals: R1(+1d), R2(+2d), R3(+4d), R4(+7d), R5(+15d), R6(+30d)
- Hot 100 split into 5 phases: P1 数组/哈希/双指针/二分/滑窗 (25题) → P2 链表 (11题) → P3 二叉树 (14题) → P4 栈队列/贪心/DP (26题) → P5 回溯/图/并查集/堆/位运算/数学 (24题)
- Target: 100 problems by late August 2026 (~12 weeks)
- Max 1-2 net new problems per day
