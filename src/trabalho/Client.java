/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author nuno1
 */
public class Client {

    Manager man;

    /**
     *
     * @param man
     */
    public Client(Manager man) {

        this.man = man;
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String regHost = "localhost";
        String regPort = "9000";

        if (args.length == 2) {

            regHost = args[0];
            regPort = args[1];
        } else {

            System.out.println("A usar valores de host e porto por definição.");
        }

        try {

            Manager man = (Manager) java.rmi.Naming.lookup("rmi://" + regHost + ":"
                    + regPort + "/manager");

            Client cl = new Client(man);

            cl.menu();

        } catch (Exception ex) {
        }

    }

    /**
     *
     * @throws Exception
     */
    public void menu() throws Exception {

        System.out.println("Menu -- Escolher opção");
        Scanner scan = new Scanner(System.in);

        int option = 1;

        while (option != 0) {

            System.out.println("1 -- Criar Questionário\n2 -- Consultar Questionários\n"
                    + "3 -- Apagar Questionário\n4 -- Obter Lista de Perguntas\n"
                    + "5 -- Submeter Resposta\n6 -- Consultar Nº de Submissões\n"
                    + "7 -- Obter Média de Valores\n0 -- Sair");

            try {

                System.out.print("Opção: ");

                option = scan.nextInt();

                this.applyAction(option);

            } catch (Exception e) {

                scan.nextLine();
                System.out.println("Opção inválida");
            }

        }
    }

    /**
     *
     * @param option
     * @throws Exception
     */
    public void applyAction(int option) throws Exception {

        switch (option) {

            case 0:
                break;

            case 1:
                try {
                    this.createSurvey();

                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }

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

                System.out.println("Opção: " + option + " desconhecida.\n"
                        + "Por favor escolha uma opção entre 0 e 7.");

                break;
        }
    }

    private void meanValues() throws RemoteException {
        Scanner scan = new Scanner(System.in);

        System.out.print("ID de questionário a consultar: ");

        int id_consult = scan.nextInt();

        Vector<QuestionAvg> avgQuest = this.man.average_Survey(id_consult);

        if (avgQuest.size() > 0) {

            System.out.println("\tResultados:");

            for (QuestionAvg q : avgQuest) {

                System.out.println("\t\t" + q.question + ": " + q.avg);
            }
        } else {

            System.out.println("Questionário " + id_consult + " não encontrado.");
        }

    }

    private void consultNumberSubmissions() throws RemoteException {

        Scanner scan = new Scanner(System.in);

        System.out.print("ID de questionário a consultar: ");

        int id_consult = scan.nextInt();

        int subs = this.man.consult_numbers_answers(id_consult);

        if (subs >= 0) {
            System.out.println("O questionário foi respondido: " + subs + " vezes.");
        } else {
            System.out.println("Questionário " + id_consult + " não encontrado.");
        }
    }

    private void submitAnswer() throws RemoteException {

        Scanner scan = new Scanner(System.in);

        System.out.print("ID de questionário a responder: ");

        int id_to_answer = scan.nextInt();

        Vector<Answer> questions = this.man.getQuestions(id_to_answer);

        if (questions.size() > 0) {

            int i = 0;

            while (i < questions.size()) {

                Answer ans = questions.get(i);

                System.out.print("\t" + ans.getQuestion() + ": ");

                try {

                    int response = scan.nextInt();

                    if (response >= 1 && response <= 10) {

                        ans.setAnswers(response);
                        i++;
                    } else {

                        System.out.println("Por favor insira um valor entre 1 e 10.");
                    }
                    
                } catch (Exception e) {
                    
                    System.out.println("Por favor insira um valor entre 1 e 10.");
                    scan.nextLine();
                }

            }

            int subCode = this.man.answersSurvey(questions, id_to_answer);

            System.out.println("O seu código de submissão: " + subCode);

        } else {

            System.out.println("Questionário " + id_to_answer + " não encontrado.");
        }

    }

    private void listSurvey() throws RemoteException {

        Scanner scan = new Scanner(System.in);

        System.out.print("ID de questionário a consultar: ");

        int id_consult = scan.nextInt();

        Questions q = this.man.questionsSurvey(id_consult);

        if (q.questions.size() > 0) {

            System.out.println("Questões do questionário " + id_consult + " são:");

            for (int i = 0; i < q.num_questions; i++) {

                System.out.println("\tPergunta " + (i + 1) + " -- " + q.questions.get(i));
            }
        } else {

            System.out.println("Questionário " + id_consult + " não encontrado.");
        }

    }

    private void deleteSurvey() throws RemoteException {

        Scanner scan = new Scanner(System.in);

        System.out.print("ID de questionário a remover: ");

        int id_remove = scan.nextInt();

        int deleted = this.man.deleteSurvey(id_remove);

        if (deleted == 0) {

            System.out.println("Questionário " + id_remove + " não encontrado.");

        } else {

            System.out.println("Questionário " + id_remove + " apagado.");

        }

    }

    private void consultSurvey() throws RemoteException {

        Vector<Integer> result = this.man.consultSurvey();

        if (result.size() > 0) {

            for (Integer i : result) {

                System.out.println("Questionário ID: " + i);
            }
        } else {

            System.out.println("Não há questionários a listar.");
        }

    }

    private void createSurvey() throws Exception {

        Scanner scan = new Scanner(System.in);

        System.out.print("Número de perguntas: ");

        int num_quest = scan.nextInt();

        if (num_quest >= 3 && num_quest <= 5) {

            Scanner scanQuests = new Scanner(System.in);

            String[] questions = new String[num_quest];

            for (int i = 0; i < num_quest; i++) {

                System.out.print("Pergunta " + (i + 1) + ": ");

                questions[i] = scanQuests.nextLine();
            }

            Survey sur = new Survey(num_quest, questions);

            this.man.insertSurvey(sur);

        } else {

            throw new Exception("Number of questions out of limit");
        }

        System.out.println("Questionário criado.");
    }
}
