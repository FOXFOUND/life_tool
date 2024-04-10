package list;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class ListRemoveTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(JSON.toJSONString(list));
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).intValue() == 1){
                list.remove(new Integer(1));
            }
            System.out.println(list.get(i));

        }
        System.out.println(JSON.toJSONString(list));
    }
}
