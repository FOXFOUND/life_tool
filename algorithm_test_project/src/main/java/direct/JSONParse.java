package direct;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class JSONParse {
    public static void main(String[] args) {
        //["010","021","020","0755","0512","022","023","0571","0510","028","0532","0411","027","025","024","0451","0311","0531","0591","0431","0371","0769","0731","0592","0752","0351","0551","0771"]
        String str ="[\"010\",\"021\",\"020\",\"0755\",\"0512\",\"022\",\"023\",\"0571\",\"0510\",\"028\",\"0532\",\"0411\",\"027\",\"025\",\"024\",\"0451\",\"0311\",\"0531\",\"0591\",\"0431\",\"0371\",\"0769\",\"0731\",\"0592\",\"0752\",\"0351\",\"0551\",\"0771\"]";
        List<String> json = JSON.parseArray(str,String.class);
        System.out.println(json.size());

    }
}