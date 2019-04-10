/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.util.Scanner;

/**
 *
 * @author nuno1
 */
public class Client {
    
    
    public Client() {
        
        
    }
    
    public static void main(String[] args) throws Exception {
        
        Client cl = new Client();
        
        cl.menu();
    }
    
    public void menu() throws Exception {
        
        System.out.println("Menu -- Escolher opção");
        Scanner scan = new Scanner(System.in);
        
        int option = 1;
        
        while (option != 0) {
            
            System.out.println("1 -- Criar Questionário\n2 -- Consultar Questionário\n"
                    + "3 -- Apagar Questionário\n4 -- Obter Lista de Perguntas\n"
                    + "5 -- Submeter Resposa\n6 -- Consultar Nº de Submissões\n"
                    + "7 -- Obter Média de Valores\n0 -- Sair");
            
            option = scan.nextInt();
            
            this.applyAction(option);
        }
    }
    
    public void applyAction(int option) throws Exception {
        
        switch(option) {
            
            case 1:
                
                this.createSurvey();
                
                break;
                
            case 2:
                
                this.consultSurvey();
                
                break;
                
            case 3:
                
                this.deleteSurvey();
                
                break;
                
            case 4:
                
                this.listSurvey();
                
                break;
                
            case 5:
                
                this.submitAnswer();
                
                break;
                
            case 6:
                
                this.consultNumberSubmissions();
                
                break;
            case 7:
                
                this.meanValues();
                
                break;
                
            default:
                break;
        }
    }

    private void meanValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void consultNumberSubmissions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void submitAnswer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listSurvey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void deleteSurvey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void consultSurvey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createSurvey() throws Exception {
        
        Scanner scan = new Scanner(System.in);
        Scanner scanQuests =  new Scanner(System.in);
        
        int num_quest = scan.nextInt();
        
        if (num_quest >= 3 && num_quest <= 5) {
            
            String[] questions = new String[num_quest];
        
            for(int i = 0; i < num_quest; i++) {

                questions[i] = scanQuests.nextLine();
            }
            
            
            
        } else {
            
            throw new Exception("Number of questions out of limit");
        }
        
        
        
        
    }
} 
