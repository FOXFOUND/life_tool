package timetest;

import java.util.Calendar;

public class TimeTest {
    public static void main(String[] args) {
        Calendar now = Calendar.getInstance();
        System.out.println(now.getTimeInMillis());
    }
}
