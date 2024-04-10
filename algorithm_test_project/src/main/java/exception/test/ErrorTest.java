package exception.test;

public class ErrorTest extends Error{

    private static final long serialVersionUID = -351488225420878020L;



    public ErrorTest(){
        super();
    }

    public ErrorTest(String msg){
        super(msg);
    }

    public static void main(String[] args) {
        try {
            throw new ErrorTest("test catch error");
        } catch (Throwable t) {
            System.out.println("step in the catch ~");
            t.printStackTrace();
        }
    }
}
