package algo.y2026.M07.d03;

import java.util.ArrayDeque;
import java.util.Deque;

// 题目 有效的括号
// 示例 s="()[]{}" => true；s="(]" => false
// leetcode https://leetcode.cn/problems/valid-parentheses/
// 算法核心 栈：左括号入栈，右括号与栈顶匹配弹出；最终栈空则有效
// 时间 O(n) 空间 O(n)
// 难度 Easy
// 标签 栈, 字符串
// 时段 早地铁
// 类型 新题

public class LC_0020_ValidParentheses {

    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((c == ')' && top != '(')
                        || (c == ']' && top != '[')
                        || (c == '}' && top != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        LC_0020_ValidParentheses s = new LC_0020_ValidParentheses();
        System.out.println(s.isValid("()[]{}"));
        System.out.println(s.isValid("(]"));
    }
}
