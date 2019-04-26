/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.rmi.RemoteException;
import java.util.Vector;

/**
 *
 * @author mt
 */
public interface Manager extends java.rmi.Remote {
    
    public void insertSurvey(Survey s) throws RemoteException;
    public Vector<Integer> consultSurvey() throws RemoteException;
    public void deleteSurvey(int id) throws RemoteException;
    public Questions questionsSurvey(int id) throws RemoteException;
    public int answersSurvey(Vector<Answer> a, int id) throws RemoteException;
    public int consult_numbers_answers(int id) throws RemoteException;
    public Vector<QuestionAvg> average_Survey(int id) throws RemoteException;
    public Vector<Answer> getQuestions(int id) throws RemoteException;
    
}
