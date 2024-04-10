package jmdi;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RmiClientTest {
    public static void main(String[] args) throws NamingException {
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase","true");
        //指定RMI服务资源的标识
        String jndi_uri = "rmi://127.0.0.1:10086/Exp";
        //构建jndi上下文环境
        InitialContext initialContext = new InitialContext();
        //查找标识关联的RMI服务
        initialContext.lookup(jndi_uri);
    }
}