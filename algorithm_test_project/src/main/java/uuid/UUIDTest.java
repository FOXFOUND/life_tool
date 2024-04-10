package uuid;

public class UUIDTest {
    public static void main(String[] args) {
        for (int i = 1; i < 15; i++) {
            String updateStr =  "update t_dict set dict_key = '" + UUIDUtils.getUUIDString() + "'" +"\twhere id = " +i + " ;" ;
            System.out.println(updateStr);
        }
    }
}
