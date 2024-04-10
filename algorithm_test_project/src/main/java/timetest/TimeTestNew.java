package timetest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeTestNew {
    public static void main(String[] args) throws InterruptedException, ParseException {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = simpleDateFormat.parse("2022-04-24 10:10:10");
        Date end = simpleDateFormat.parse("2022-04-27 10:10:10");
        long sub  = between(start,end,TimeUnit.SECONDS);
        System.out.println(sub);
    }

    public static long between(Date start, Date end, TimeUnit unit) {

        if (start == null || end == null || unit == null) {
            return 0;
        }

        System.out.println(end.getTime());
        System.out.println(start.getTime());
        long duration = end.getTime() - start.getTime();
        return unit.convert(duration, TimeUnit.MILLISECONDS);
    }
}
