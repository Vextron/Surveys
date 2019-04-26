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
public class QuestionAvg implements java.io.Serializable {
    
    String question;
    float avg;
    
    public QuestionAvg(String question, float avg) {
        
        this.question = question;
        this.avg = avg;
    }
}
