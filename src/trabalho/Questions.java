/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.util.Vector;

/**
 *
 * @author nuno1
 */
public class Questions implements java.io.Serializable {
    
    Vector<String> questions;
    int num_questions;
    
    public Questions(int num_quests, Vector<String> questions) {
        
        this.num_questions = num_quests;
        this.questions = questions;
    }
}
