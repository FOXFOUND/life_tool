package commonChars.problem;

import java.util.ArrayList;
import java.util.List;

class Solution2 {
    public List<String> commonChars(String[] A) {
        List<String> result = new ArrayList<>();
        if (A.length == 0) return result;
        int[] hash= new int[26]; // 用来统计所有字符串里字符出现的最小频率
        for (int i = 0; i < A[0].length(); i++) { // 用第一个字符串给hash初始化
            hash[A[0].charAt(i)- 'a']++;
        }
        // 统计除第一个字符串外字符的出现频率
        for (int i = 1; i < A.length; i++) {
            int[] hashOtherStr= new int[26];
            for (int j = 0; j < A[i].length(); j++) {
                hashOtherStr[A[i].charAt(j)- 'a']++;
            }
            // 更新hash，保证hash里统计26个字符在所有字符串里出现的最小次数
            for (int k = 0; k < 26; k++) {
                hash[k] = Math.min(hash[k], hashOtherStr[k]);
            }
        }
        // 将hash统计的字符次数，转成输出形式
        for (int i = 0; i < 26; i++) {
            while (hash[i] != 0) { // 注意这里是while，多个重复的字符
                char c= (char) (i+'a');
                result.add(String.valueOf(c));
                hash[i]--;
            }
        }
        return result;
    }
}

//作者：carlsun-2
//        链接：https://leetcode.cn/problems/find-common-characters/solution/1002-cha-zhao-chang-yong-zi-fu-ha-xi-fa-jing-dian-/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
