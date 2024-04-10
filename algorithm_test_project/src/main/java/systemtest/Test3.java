package systemtest;

import java.util.ArrayList;

public class Test3 {
    public static void main(String[] args) {
        int i=0,s=0;
        do{
            if(i %2 == 0){
                i++;
                continue;
            }
            i++;
            s = s +i;
        }while (i < 7);
        System.out.println(s);
        ArrayList arrayList = new ArrayList();

    }
}
