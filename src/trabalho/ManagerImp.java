/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mt
 */
public class ManagerImp extends UnicastRemoteObject implements Manager,java.io.Serializable{
    
    Statement s;
    
    public ManagerImp(Statement s) throws RemoteException
    {
     
        super();
        this.s = s;
    }
    
    @Override
    public void insertSurvey(Survey s) {
        
        try {
            int id = this.s.executeUpdate("insert into questionario values(0) returning ID");
            
            for(int i=0;i<s.num_quests;i++)
            {
                this.s.executeUpdate("insert into perguntas values ("+ id+","+ s.questions[i]+")");
            }
            
            
        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Survey consultSurvey(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSurvey(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Questions questionsSurvey(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void answersSurvey(Answer a, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int consult_numbers_answers(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] average_Survey(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  
    
}
