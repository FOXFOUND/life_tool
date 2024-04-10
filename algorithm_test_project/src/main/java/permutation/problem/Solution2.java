package permutation.problem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Solution2 {

    Set<String> res = new HashSet<>();

    public String[] permutation(String s) {


        char[] arr = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            arr[i] = s.charAt(i);
        }

        dfs(arr, 0);
        String[] resArr = new String[res.size()];
        Iterator<String> iterator = res.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            resArr[index] = iterator.next();
            index++;
        }
        return resArr;

    }

    private void dfs(char[] arr, int level) {

        if (level >= arr.length) {
            return;
        }

        if (level == arr.length - 1) {
            String s = new String(arr);
            res.add(s);
            return;
        }

        for (int i = level; i < arr.length; i++) {
            swap(arr, level, i);
            dfs(arr, level + 1);
            swap(arr, i, level);
        }

    }

    private void swap(char[] arr, int left, int right) {
        char temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }
}
