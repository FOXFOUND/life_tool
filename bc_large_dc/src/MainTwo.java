import java.math.BigDecimal;

public class MainTwo {

    //根据新的预期收益率,计算卖出价格和持有收益率
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

        //新的预期收益率
        double newIncomePercentD = 0.024057d;

        System.out.println("存单到期本息和 = " + endDcIncome);
        System.out.println("买入方的新收益率 = " + String.format("%.6f", newIncomePercentD));
        System.out.println("买入价格 = " + buyPrice);
        System.out.println("持有天数 = " + keepDays);
        System.out.println("存单到期剩余天数 = " + remainDays);


        BigDecimal newIncomePercent = new BigDecimal(newIncomePercentD);
        BigDecimal sellPriceBD = new BigDecimal(endDcIncome)
                .multiply(new BigDecimal(365)).
                divide(newIncomePercent
                                .multiply(new BigDecimal(remainDays))
                                .add(new BigDecimal(365)),
                        6
                        , BigDecimal.ROUND_HALF_UP);

        //卖出价格
        double sellPrice = sellPriceBD.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

        System.out.println("卖出价格 = " + String.format("%.2f", sellPrice));
        //持有期间的收益率
        BigDecimal incomePercent =  new BigDecimal(sellPriceBD.setScale(6, BigDecimal.ROUND_HALF_UP).intValue())
                .divide(new BigDecimal(buyPrice),6
                , BigDecimal.ROUND_HALF_UP)
                .subtract(new BigDecimal(1))
                .multiply(new BigDecimal(365))
                .divide(new BigDecimal(keepDays),6
                        , BigDecimal.ROUND_HALF_UP);

        double incomePercentD = incomePercent.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("买入期间预期收益率 = " + String.format("%.6f", incomePercentD));

        System.out.println("计算结束");
    }
}
