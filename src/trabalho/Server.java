/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Properties;

/**
 *
 * @author nuno1
 */
public class Server {

    public static void main(String[] args) throws RemoteException, Exception {

        int regPort = 9000;

        InputStream in = new FileInputStream("./conf.properties");

        Properties prop = new Properties();

        prop.load(in);

        String url = prop.getProperty("db.url", "localhost");
        String user = prop.getProperty("db.user");
        String database = prop.getProperty("db.database");
        String psw = prop.getProperty("db.psw");

        ManagerImp man = new ManagerImp(url, user, database, psw);

        java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);

        registry.rebind("manager", man);

        System.out.println("Ready to Go!");

    }
}
