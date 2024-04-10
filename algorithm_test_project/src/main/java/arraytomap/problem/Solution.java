package arraytomap.problem;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    public static void main(String[] args) {

        List<String> array = Arrays.asList(
                "etc->hosts",
                "etc->kubernetes->ssl->certs",
                "root");

        Map<String, Map> mapTree = arrayToMap(array);

        /**
         * {
         * 	"etc": {
         * 		"kubernetes": {
         * 			"ssl": {
         * 				"certs": {}
         *                        }* 		},
         * 		"hosts": {}
         * 	},
         * 	"root": {}
         * }
         */
        System.out.println(JSON.toJSONString(mapTree));
    }

    private static Map<String, Map> arrayToMap(List<String> array) {

        Map<String, Map> res = new HashMap<>();

        for (int i = 0; i < array.size(); i++) {
            String temp = array.get(i);
            String[] arrTemp = temp.split("->");
            Map<String, Map> tempMap = res;
            for (int j = 0; j < arrTemp.length; j++) {

                if (tempMap.get(arrTemp[j]) == null) {
                    tempMap.put(arrTemp[j], new HashMap());
                }
                tempMap = tempMap.get(arrTemp[j]);
            }
        }

        return res;
    }
}
