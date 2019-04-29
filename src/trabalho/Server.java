/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author nuno1
 */
public class Server {

    /**
     *
     * @param args
     * @throws RemoteException
     * @throws Exception
     */
    public static void main(String[] args) throws RemoteException, Exception {

        int regPort = 9000;

        try {
            InputStream in = new FileInputStream("./conf.properties");

            Properties prop = new Properties();

            prop.load(in);

            String url = prop.getProperty("db.url", "localhost");
            String user = prop.getProperty("db.user");
            String database = prop.getProperty("db.database");
            String psw = prop.getProperty("db.psw");

            Server.checkTables(url, user, database, psw);

            ManagerImp man = new ManagerImp(url, user, database, psw);

            java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);

            registry.rebind("manager", man);

            System.out.println("Ready to Go!");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     *
     * @param host
     * @param user
     * @param database
     * @param psw
     * @throws Exception
     */
    public static void checkTables(String host, String user, String database, String psw) throws Exception {

        PostgresConnector pc = new PostgresConnector(host, database, user, psw);

        pc.connect();
        
        try (Statement state = pc.getStatement()) {
            ResultSet set = pc.con.getMetaData().getTables(null, null, "surveys", null);
            
            if (!set.next()) {
                
                System.out.println("Tabela surveys não encontrada. A criar...");
                String createSurveys = "create table surveys(id serial primary key, numans integer);";
                
                state.executeUpdate(createSurveys);
                
                System.out.println("Tabela criada");
                
            } else {
                
                System.out.println("Tabela surveys existe.");
            }
            
            set = pc.con.getMetaData().getTables(null, null, "questions", null);
            
            if (!set.next()) {
                
                System.out.println("Tabela questions não encontrada. A criar...");
                String createSurveys = "create table questions(id serial primary key, surveyid integer "
                        + "references surveys (id) on delete cascade, question varchar);";
                
                state.executeUpdate(createSurveys);
                
                System.out.println("Tabela criada");
                
            } else {
                
                System.out.println("Tabela questions existe.");
            }
            
            set = pc.con.getMetaData().getTables(null, null, "submission_codes", null);
            
            if (!set.next()) {
                
                System.out.println("Tabela submission_codes não encontrada. A criar...");
                String createSurveys = "create table submission_codes(codesub serial primary key);";
                
                state.executeUpdate(createSurveys);
                
                System.out.println("Tabela criada");
                
            } else {
                
                System.out.println("Tabela submission_codes existe.");
            }
            
            set = pc.con.getMetaData().getTables(null, null, "answers", null);
            
            if (!set.next()) {
                
                System.out.println("Tabela answers não encontrada. A criar...");
                String createSurveys = "create table answers("
                        + "id serial primary key, codsub integer references submission_codes(codesub) on delete cascade, "
                        + "questionid integer references questions(id) on delete cascade, surveyid integer references surveys(id) on delete cascade, "
                        + "value integer);";
                
                state.executeUpdate(createSurveys);
                
                System.out.println("Tabela criada");
                
            } else {
                
                System.out.println("Tabela answers existe.");
            }
            
            set.close();
        }

        pc.disconnect();
    }
}
