package systemtest;

import java.util.List;

public class Test {

    private void method(List<?> list) {
    }

//    public void method1(List<T> list) {
//    }

    private <T> void method2(List list) {
    }
}
