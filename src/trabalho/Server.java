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
    
    public static void main(String[] args) throws RemoteException {
        
        int regPort= 1099;
        
        Manager man = new ManagerImp();
        
        java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);
        
        registry.rebind("manager", man);  

    }
}
