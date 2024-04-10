package permute.problem;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    /**
     * 该算法使用了一个列表来保存当前生成的所有排列，初始时，只有一个空列表。
     * 然后，从第一个数字开始，每次将其插入到每个已存在的排列的每个位置，并生成新的排列。
     * 然后，将新的排列添加到当前列表中，
     * 并将当前列表更新为新的排列列表。重复这个过程，直到遍历完所有数字，最后得到所有可能的排列。
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<List<Integer>> result = permute(nums);
        System.out.println(result);
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        //初始化一个空数组
        result.add(new ArrayList<>());

        for (int i = 0; i < nums.length; i++) {
            List<List<Integer>> current = new ArrayList<>();
            for (List<Integer> l : result) {
                for (int j = 0; j < l.size() + 1; j++) {
                    l.add(j, nums[i]);
                    List<Integer> temp = new ArrayList<>(l);
                    current.add(temp);
                    //l的作用有两个,一个是生成新的快照,另一个是当前的快照,所以生成新快照之后,要防止 j < l.size 失效
                    l.remove(j);
                }
            }

            //保留最近结果
            result = new ArrayList<>(current);
        }
        return result;
    }
}
