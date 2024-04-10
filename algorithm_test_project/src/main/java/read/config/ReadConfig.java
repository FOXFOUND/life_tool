package read.config;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadConfig {
    public static void main(String[] args) throws IOException {
        //String filePath = "/Users/58_project/crm_svc_biz/src/main/java/com/bj58/crm/biz/httpinvoker/ConfigApi.java";
       // String filePath = "/Users/58_project/crm_svc_biz/src/main/java/com/bj58/crm/biz/httpinvoker/ReleaseRuleApi.java";
        String filePath = "/Users/58_project/crm_pc/src/main/java/com/bj58/crm/pc/httpinvoker/ConfigApi.java";
        String s = FileUtils.readFileToString(new File(filePath));
        //System.out.println(s);
        String p1 = "@HttpReq[\\S\\s\\r]*?;";
        String p2 = "HttpReq.*\"(.*)\"";
        String p3 = "(\\S*)[\\s]+\\S*?\\((.*)\\);";
        String preFix = "";
        Pattern r1 = Pattern.compile(p1);
        Matcher m1 = r1.matcher(s);
        Pattern r2 = Pattern.compile(p2);
        Pattern r3 = Pattern.compile(p3);
        while (m1.find()) {
            String line = m1.group(0);
            Matcher m2 = r2.matcher(line);
            Matcher m3 = r3.matcher(line);
            if (m2.find()) {
                System.out.print(preFix + m2.group(1) + "\t");
            }
            if (m3.find()) {
                System.out.print(m3.group(1) + "\t");
                System.out.print(m3.group(2) + "\t");
            }
            System.out.println("");
        }
        //Matcher matcher =  new Match
    }
}
