package divide.players.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public long dividePlayers(int[] skill) {
        if ((skill.length % 2) != 0) {
            return -1;
        }
        Arrays.sort(skill);
        int left = 0, right = skill.length - 1, sum = skill[left] + skill[right];
        List<ArrayList<Integer>> arrayLists = new ArrayList<>();
        while (left < right) {
            if (skill[left] + skill[right] == sum) {
                ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(skill[left]);
                arrayList.add(skill[right]);
                arrayLists.add(arrayList);
                left++;
                right--;
            } else {
                return -1;
            }
        }
        long res = 0;
        for (int i = 0; i < arrayLists.size(); i++) {
            res += arrayLists.get(i).get(0) * arrayLists.get(i).get(1);
        }
        return res;
    }

    public static void main(String[] args) {
        int [] nums = {3,2,5,1,3,4};
        Solution solution = new Solution();
        long res = solution.dividePlayers(nums);
        System.out.println(res);
    }
}
