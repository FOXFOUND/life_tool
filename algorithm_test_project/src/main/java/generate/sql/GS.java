package generate.sql;

public class GS {


    public static void main(String[] args) {
        String sqlA = "SELECT \n" +
                "\t*\n" +
                "FROM\n";
        String sqlB = "WHERE\n" +
                "\tlibrary_id IN ( 1, 2, 5, 6, 7, 8, 9, 10 ) \n" +
                "\tAND opp_status IN ( 1, 2, 3, 4 ) \n" +
                "\tAND product_line IN ( 55, 56, 65, 67 ) \n" +
                "GROUP BY\n" +
                "\tlibrary_id,\n" +
                "\topp_status,\n" +
                "\tproduct_line";
        String pre = "t_opportunity_";
        String back = "\n union all \n";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(sqlA);
            stringBuilder.append(pre + i+ "\n");
            stringBuilder.append(sqlB);
            if (i != 15) {

                stringBuilder.append(back);
            }
        }
        System.out.println(stringBuilder.toString());
    }
}
