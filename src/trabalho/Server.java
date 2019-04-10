/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.rmi.RemoteException;

/**
 *
 * @author nuno1
 */
public class Server {
    
<<<<<<< HEAD
    public static void main(String[] args) throws RemoteException {
=======
    public static void main(String[] args) throws RemoteException, Exception {
>>>>>>> f09cc70c535ef89fbd9cc34991fc05dc23a9e7b3
        
        int regPort= 1099;
        PostgresConnector pc = new PostgresConnector("alunos.di.uevora.pt","l37508","l37508","migueltavares");
        pc.connect();
        
        ManagerImp man = new ManagerImp(pc.getStatement());
        
        java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);
        
        registry.rebind("manager", man);  

    }
}
