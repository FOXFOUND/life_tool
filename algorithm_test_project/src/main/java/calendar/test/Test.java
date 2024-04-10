package calendar.test;

import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        calendar.add(Calendar.MINUTE,5);
        calendar.set(Calendar.SECOND,0);
        System.out.println(calendar.getTime());
        calendar.clear();
        System.out.println(calendar.getTime());
    }
}
