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

    /**
     *
     * @param id
     * @param surveyid
     * @param question
     */
    public Answer(int id, int surveyid, String question) {

        this.id = id;
        this.surveyid = surveyid;
        this.question = question;
    }

    /**
     *
     * @param op
     */
    public void setAnswers(int op) {

        this.answer = op;
    }
    
    /**
     *
     * @return
     */
    public int getId() {
        
        return this.id;
    }
    
    /**
     *
     * @return
     */
    public int getSurveyId() {
        
        return this.surveyid;
    }
    
    /**
     *
     * @return
     */
    public int getAnswer() {
        
        return this.answer;
    }
    
    /**
     *
     * @return
     */
    public String getQuestion() {
        
        return this.question;
    }

}
