/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

/**
 *
 * @author mt
 */
public interface Manager extends java.rmi.Remote {
    
    public void insertSurvey(Survey s);
    public Survey consultSurvey(int id);
    public void deleteSurvey(int id);
    public Questions questionsSurvey(int id);
    public void answersSurvey(Answer a,int id);
    public int consult_numbers_answers(int id);
    public int[] average_Survey(int id);
    
}
