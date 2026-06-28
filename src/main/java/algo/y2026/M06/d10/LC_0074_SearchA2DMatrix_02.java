package algo.y2026.M06.d10;

// 题目 搜索二维矩阵（每行递增，每行首 > 上行末）
// 示例 [[1,3,5,7],[10,11,16,20],[23,30,34,60]], 3 => true
// leetcode https://leetcode.cn/problems/search-a-2d-matrix/
// 算法核心 二维转一维二分，mid/n 得行，mid%n 得列
// 执行过程 matrix=[[1,3,5,7],[10,11,16,20],[23,30,34,60]], target=3
//   m=3 n=4 left=0 right=11
//   mid=5 → row=5/4=1 col=5%4=1 val=11>3 → right=4
//   mid=2 → row=2/4=0 col=2%4=2 val=5>3 → right=1
//   mid=0 → row=0/4=0 col=0%4=0 val=1<3 → left=1
//   mid=1 → row=1/4=0 col=1%4=1 val=3==3 → return true ✔
// 时间 O(log(m*n)) 空间 O(1)
// 难度 Medium
// 标签 数组, 二分查找, 矩阵
// Java语法实现
//   - 先判 matrix==null / length==0 / matrix[0].length==0
//   - int row = mid / n, int col = mid % n
//   - 注意 n=matrix[0].length 不是 matrix.length
// 时段 早地铁
// 类型 新题

public class LC_0074_SearchA2DMatrix_02 {

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

        int m = matrix.length, n = matrix[0].length;
        int left = 0, right = m * n - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int row = mid / n;
            int col = mid % n;
            int val = matrix[row][col];

            if (val == target) {
                return true;
            } else if (val < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LC_0074_SearchA2DMatrix_02 s = new LC_0074_SearchA2DMatrix_02();

        int[][] m1 = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        System.out.println("Test 1: " + s.searchMatrix(m1, 3));
        System.out.println("Test 2: " + s.searchMatrix(m1, 13));
        System.out.println("Test 3: " + s.searchMatrix(new int[][]{{1}}, 1));
        System.out.println("Test 4: " + s.searchMatrix(new int[][]{}, 1));
        System.out.println("Test 5: " + s.searchMatrix(null, 1));
    }
}
