package exception.test;

public class ExceptionT {
    class  Person{

        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static void main(String[] args) {

        try{
            Person person = null;
            new Exception(person.getMsg());
        }catch (Exception e){
            System.out.println("ok");
            e.printStackTrace();
        }
        System.out.println("ok1");
    }
}
