package checkStraightLine.problem;

class Solution2 {

    /**
     * 向量的点积
     * @param coordinates
     * @return
     */
    public boolean checkStraightLine(int[][] coordinates) {
        for(int i = 2;i < coordinates.length;i++){
            int[] a = {coordinates[i - 1][0] - coordinates[i - 2][0],
                    coordinates[i - 1][1] - coordinates[i - 2][1]};
            int[] b = {coordinates[i][0] - coordinates[i - 2][0],
                    coordinates[i][1] - coordinates[i - 2][1]};
            if(a[0] * b[1] - a[1] * b[0] != 0)
                return false;
        }
        return true;
    }
}

//作者：isliujiao
//        链接：https://leetcode.cn/problems/check-if-it-is-a-straight-line/solution/1232zhui-dian-cheng-xian-shu-xue-gong-sh-5k5c/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
