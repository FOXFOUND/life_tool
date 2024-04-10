package referenece.test;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class ReferenceTest {
    public static void main(String[] args) {
        List a = new ArrayList<Integer>();
        a.add(1);
        a.add(2);
        initList(a);
        //initListNew(a);
        System.out.println(JSON.toJSONString(a));
    }

    private static void initListNew(List a) {
        a.add(3);
        a.add(4);
    }

    private static void initList(List a) {
        a = new ArrayList();
        a.add(3);
        a.add(4);
        System.out.println(JSON.toJSONString(a));
    }
}
