import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainThree {

    // 计算大额订单持有天数和剩余天数
    public static void main(String[] args) {

        String buyStr = "2023-08-30";
        String sellStr = "2024-04-11";
        String endStr = "2026-08-30";
        System.out.println("买入时间 = " + buyStr);
        System.out.println("卖出时间 = " + sellStr);
        System.out.println("到期时间 = " + endStr);
        //买入日期
        LocalDate localDateBuy = LocalDate.parse(buyStr, DateTimeFormatter.ISO_DATE);

        //卖出日期
        LocalDate localDateSell = LocalDate.parse(sellStr, DateTimeFormatter.ISO_DATE);

        //到期时间
        LocalDate localDateEnd = LocalDate.parse(endStr, DateTimeFormatter.ISO_DATE);

        //持有天数
        Long keepDays = localDateSell.toEpochDay() - localDateBuy.toEpochDay();
        System.out.println("持有时间 = " + keepDays);

        //剩余天数
        Long remainDays= localDateEnd.toEpochDay() -  localDateSell.toEpochDay();
        System.out.println("剩余时间 = " + remainDays);



    }
}
