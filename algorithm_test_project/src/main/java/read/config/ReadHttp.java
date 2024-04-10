package read.config;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadHttp {
    public static void main(String[] args) throws IOException {
        String s = "/Users/58_project/58_testproject/src/main/resources/readhttp/1.txt";
        List<String> list = FileUtils.readLines(new File(s));
        System.out.println(list.size());
        Map<String, String> stringStringMap = new HashMap<>();
        for (String temp : list) {
            String[] arr = temp.split("\t");
            try {
                //String s2 =arr[1];
                if (stringStringMap.get(arr[1]) == null) {
                    stringStringMap.put(arr[1], temp);
                } else {
                    System.out.println("重复:" + temp);
                }

            } catch (Exception e) {
                System.out.println(temp);
            }
        }
        System.out.println(stringStringMap.size());
        stringStringMap.forEach((k,v)->{
            System.out.println(v);
        });
//        List<String> dList = list.stream().distinct().collect(Collectors.toList());
//        System.out.println(dList.size());
//        list.removeAll(dList);

    }
}
