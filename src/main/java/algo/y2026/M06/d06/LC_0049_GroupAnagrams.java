package algo.y2026.M06.d06;

import java.util.*;

// 题目 给你一个字符串数组，请你把字母异位词组合在一起，可以安装任意顺序结果返回
// 示例 strs=["eat","tea","tan","ate","nat","bat"] = > 输出 [["bat"],["nat","tan"],["ate","tea","eat"]]
// leetcode https://leetcode.cn/problems/group-anagrams/description/
// 算法核心 排序法：异位词排序后 key 相同，用 HashMap 按 key 分组；
// 遍历每个字符串，排序作 key，相同 key 归入同一列表，不同 key 新建分组；
// 最终返回 map 中所有分组列表。
// 时间nk*logk 空间nk
// 难度 Medium
// 标签
// 复盘
// 时段 13:50-14:30
// 类型 新题
public class LC_0049_GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs){
        if(strs == null || strs.length == 0) return new ArrayList<>();
        Map<String,List<String>> map = new HashMap<>();
        for(String item : strs){
            char[] c = item.toCharArray();
            Arrays.sort(c);
            String key = new String(c);
            map.computeIfAbsent(key,k -> new ArrayList<>()).add(item);
        }
        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        // 正常
        String[] arr = {"abc","dca","cba"};
        LC_0049_GroupAnagrams solution = new LC_0049_GroupAnagrams();
        System.out.println(solution.groupAnagrams(arr));

        // 为空
        String[] arr1 = {};
        System.out.println(solution.groupAnagrams(arr1));
    }
}
// 暴力法 理解至上 先排序后hash分组
// 初始数据 ["abc","bca","ade"]
// 字符串数组转换字符数组，再排序，["abc","abc","ade"]
// 遍历字符串数组，排序，如果存在hash，添加原始数据，也是添加 如何确定新旧hash while(hash
// 思考过程：13:50-14:10 出现的 问题：无法确定新旧hash