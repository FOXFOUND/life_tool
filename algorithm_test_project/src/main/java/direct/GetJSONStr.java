package direct;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetJSONStr {
    public static void main(String[] args) throws IOException {
        String path = GetJSONStr.class.getClassLoader().getResource("").getPath() + "1.txt";
        List<String> areadCode = new ArrayList<>();
        List<String> list = FileUtils.readLines(new File(path));
        for (String s : list){
            System.out.println(s.split("\t")[0]);
            System.out.println(s.split("\t")[1]);
            areadCode.add(s.split("\t")[1])  ;
        }
        System.out.println(JSON.toJSONString(areadCode));
    }
}
