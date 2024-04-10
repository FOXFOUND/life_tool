package commonChars.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<String> commonChars(String[] words) {

        String startStr = words[0];
        char[] chars = startStr.toCharArray();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            int nums = 0;
            for (int j = 0; j < words.length; j++) {
                for (int k = 0; k < words[j].length(); k++) {
                    //判断当前字符,在其他字符串中是否存在
                    if (words[j].charAt(k) == chars[i]) {
                        nums++;
                        break;
                    }
                }
            }
            //在每个字符串中都存在
            if (nums == words.length) {
                res.add(String.valueOf(chars[i]));
                for (int j = 0; j < words.length; j++) {
                    words[j] = words[j].replaceFirst(String.valueOf(chars[i]), "-1");
                }
            }


        }

        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] words = new String[]{"cool", "lock", "cook"};
        List<String> res = solution.commonChars(words);
        System.out.println(JSON.toJSONString(res));
    }
}
