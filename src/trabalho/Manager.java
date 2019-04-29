/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author mt
 */
public interface Manager extends java.rmi.Remote {
    
    /**
     *
     * @param s
     * @throws RemoteException
     */
    public void insertSurvey(Survey s) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public Vector<Integer> consultSurvey() throws RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public int deleteSurvey(int id) throws RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public Questions questionsSurvey(int id) throws RemoteException;

    /**
     *
     * @param a
     * @param id
     * @return
     * @throws RemoteException
     */
    public int answersSurvey(Vector<Answer> a, int id) throws RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public int consult_numbers_answers(int id) throws RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public Vector<QuestionAvg> average_Survey(int id) throws RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public Vector<Answer> getQuestions(int id) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void disconnectDB() throws RemoteException;
    
}
