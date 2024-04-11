import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        System.out.println("开始计算");

        //买入价格
        int buyPrice = 6000000;
        //存单到期本息和
        int endDcIncome = 6522000;
        //持有天数
        int keepDays = 225;
        //存单到期剩余天数
        int remainDays = 871;
        //买入期间预期收益率 , 保留两位小数
        double incomePercentD = 0.0454;
        BigDecimal incomePercent = new BigDecimal(incomePercentD);


        System.out.println("存单到期本息和 = " + endDcIncome);
        System.out.println("买入价格 = " + buyPrice);
        System.out.println("买入期间预期收益率 = " + String.format("%.6f", incomePercentD));
        System.out.println("持有天数 = " + keepDays);
        System.out.println("存单到期剩余天数 = " + remainDays);
        double sellPrice = buyPrice * (1 + incomePercent.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue() / 365 * keepDays);
        BigDecimal sellPriceBD = new BigDecimal(sellPrice);
        sellPrice = sellPriceBD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        System.out.println("卖出价格 = " + String.format("%.2f", sellPrice));


        BigDecimal newIncomePercentBD = new BigDecimal(endDcIncome - sellPrice)
                .multiply(new BigDecimal(365))
                .divide(new BigDecimal(remainDays), 6, BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal(sellPrice), 6, BigDecimal.ROUND_HALF_UP);

        //新的预期收益率
        double newIncomePercent = newIncomePercentBD.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

        System.out.println("买入方的新收益率 = " + String.format("%.6f", newIncomePercent));
        System.out.println("计算结束");
    }
}