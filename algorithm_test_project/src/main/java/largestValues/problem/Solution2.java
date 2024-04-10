package largestValues.problem;

import com.alibaba.fastjson.JSON;

public class Solution2 {
    public static void main(String[] args) {

        String key = "gsso.session.ST-5541111-CPs54axU5X3nzhwqdM9e-passport-58corp-com.header";
        System.out.println(JSON.toJSONString(key.getBytes()));
    }
}
