package readfile;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFileTest {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/58_project/crm_pc/src/main/java/com/bj58/crm/pc/controllers");
        File[] files = file.listFiles();
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            System.out.println(f.getName());
            String s = FileUtils.readFileToString(f);
            if (s.contains("@CrmAuth")) {
                continue;
            }
            stringList.add("com.bj58.crm.pc.controllers." + f.getName().split("\\.")[0]);
        }
        System.out.println("--------------------");
        for (int i = 0; i < stringList.size(); i++) {
            System.out.println(stringList.get(i));
        }
        System.out.println("--------------------");


    }
}
