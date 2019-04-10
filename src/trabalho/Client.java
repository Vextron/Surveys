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
    
    public static void main(String[] args) {
        
        Client cl = new Client();
        
        cl.menu();
    }
    
    public void menu() {
        
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
    
    public void applyAction(int option) {
        
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

    private void createSurvey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
} 
