/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

/**
 *
 * @author nuno1
 */
public class Answer implements java.io.Serializable {

    private final int id;
    private final int surveyid;
    private final String question;
    private int answer = 0;

    public Answer(int id, int surveyid, String question) {

        this.id = id;
        this.surveyid = surveyid;
        this.question = question;
    }

    public void setAnswers(int op) {

        this.answer = op;
    }
    
    public int getId() {
        
        return this.id;
    }
    
    public int getSurveyId() {
        
        return this.surveyid;
    }
    
    public int getAnswer() {
        
        return this.answer;
    }
    
    public String getQuestion() {
        
        return this.question;
    }

}
