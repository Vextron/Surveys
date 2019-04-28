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
public class Survey implements java.io.Serializable{
    
    int num_quests;
    String[] questions;
    
    public Survey(int num_quests, String[] questions) {
        
        this.num_quests = num_quests;
        this.questions = questions;
    }
}
