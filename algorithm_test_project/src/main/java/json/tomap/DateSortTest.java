package json.tomap;

import com.alibaba.fastjson.JSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateSortTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<DateItem> dateList = new ArrayList<>();
        DateItem dateItem = new DateItem();
        dateItem.setDate(format.parse("2018-07-24 09:53:17"));
        dateList.add(dateItem);
        DateItem dateItem2 = new DateItem();
        dateItem2.setDate(format.parse("2018-07-24 17:22:19"));
        dateList.add(dateItem2);


        System.out.println(JSON.toJSONString(dateList));
        Collections.sort(dateList, new Comparator<DateItem>() {
            @Override
            public int compare(DateItem o1, DateItem o2) {
                //return o1.getDate().compareTo(o2.getDate());
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        System.out.println(JSON.toJSONString(dateList));
    }
}
