/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mt
 */
public class ManagerImp extends UnicastRemoteObject implements Manager, java.io.Serializable {

    private final Semaphore semaphore = new Semaphore(1, true);
    String host;
    String database;
    String user;
    String psw;

    public ManagerImp(String host, String user, String db, String pw) throws RemoteException {
        super();

        this.host = host;
        this.database = db;
        this.user = user;
        this.psw = pw;
    }

    @Override
    public void insertSurvey(Survey s) throws RemoteException {

        try {
            PostgresConnector pc = new PostgresConnector(this.host, this.database, this.user, this.psw);

            this.semaphore.acquire();

            pc.connect();
            String query = "INSERT INTO surveys (numans) VALUES (0) RETURNING id;";

            Statement state = pc.getStatement();

            ResultSet set = state.executeQuery(query);

            set.next();

            int id = set.getInt(1);

            for (int i = 0; i < s.num_quests; i++) {
                String update = "INSERT INTO questions (surveyid, question) VALUES (" + id + "," + "'" + s.questions[i] + "'" + ");";

                state.executeUpdate(update);
            }

            set.close();
            state.close();
            pc.disconnect();
            this.semaphore.release();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Vector<Integer> consultSurvey() throws RemoteException {

        Vector<Integer> result = new Vector<>();

        try {
            PostgresConnector pc = new PostgresConnector(this.host, this.database, this.user, this.psw);

            pc.connect();

            Statement state = pc.getStatement();
            try {
                ResultSet rs = state.executeQuery("SELECT * from surveys;");

                while (rs.next()) {
                    int id = rs.getInt("id");

                    result.add(id);
                }
                rs.close();
                state.close();

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Problems retrieving data from db...");
            }

            pc.disconnect();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public void deleteSurvey(int id) throws RemoteException {
        try {
            PostgresConnector pc = new PostgresConnector(this.host, this.database, this.user, this.psw);

            this.semaphore.acquire();

            pc.connect();
            String query = "DELETE from surveys where id = " + id + ";";

            Statement state = pc.getStatement();

            state.executeUpdate(query);

            state.close();
            pc.disconnect();
            this.semaphore.release();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Questions questionsSurvey(int id) throws RemoteException {

        Vector<String> questions = new Vector<>();
        int i = 0;

        try {
            PostgresConnector pc = new PostgresConnector(this.host, this.database, this.user, this.psw);

            pc.connect();
            String query = "select question from questions where surveyid = " + id + ";";

            Statement state = pc.getStatement();

            ResultSet result = state.executeQuery(query);

            while (result.next()) {

                questions.add(result.getString("question"));
                i++;
            }

            result.close();
            state.close();
            pc.disconnect();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new Questions(i, questions);
    }

    @Override
    public int answersSurvey(Vector<Answer> a, int id) throws RemoteException {

        int submissionCode = -1;

        try {
            
            PostgresConnector pc = new PostgresConnector(this.host, this.database, this.user, this.psw);

            pc.connect();

            String query = "INSERT INTO submission_codes DEFAULT VALUES RETURNING codesub;";

            try (Statement state = pc.getStatement()) {
                try (ResultSet result = state.executeQuery(query)) {
                    result.next();

                    submissionCode = result.getInt(1);
                }

                this.semaphore.acquire();
                
                for (Answer ans : a) {

                    query = "INSERT INTO answers (codsub, questionid, surveyid, value) VALUES (" + submissionCode
                            + "," + ans.getId() + "," + ans.getSurveyId() + "," + ans.getAnswer() + ");";

                    state.executeUpdate(query);
                }

                query = "UPDATE surveys SET numans = numans + 1 WHERE id = " + id + ";";

                state.executeUpdate(query);
                
                this.semaphore.release();
                state.close();
            }
            pc.disconnect();
            
        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return submissionCode;
    }

    @Override
    public int consult_numbers_answers(int id) throws RemoteException {

        int numAns = -1;

        try {
            PostgresConnector pc = new PostgresConnector(this.host, this.database, this.user, this.psw);

            pc.connect();
            String query = "SELECT numans from surveys where id = " + id + ";";

            Statement state = pc.getStatement();

            ResultSet result = state.executeQuery(query);

            result.next();

            numAns = result.getInt("numans");

            result.close();
            state.close();
            pc.disconnect();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return numAns;
    }

    @Override
    public Vector<QuestionAvg> average_Survey(int id) throws RemoteException {

        Vector<QuestionAvg> quest =  new Vector<>();
        
        try {
            PostgresConnector pc = new PostgresConnector(this.host, this.database, this.user, this.psw);

            pc.connect();
            String query = "SELECT questions.question as quest, questions.surveyid as sid, avg(answers.value) AS avg_value FROM "
                    + "answers inner join questions on answers.questionid = questions.id "
                    + "GROUP BY questions.question, questions.surveyid HAVING questions.surveyid = " + id + ";";

            try (Statement state = pc.getStatement(); ResultSet result = state.executeQuery(query)) {
                
                while(result.next()) {
                    
                    quest.add(new QuestionAvg(result.getString("quest"), result.getFloat("avg_value")));
                }
                
            }
            pc.disconnect();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return quest;
    }

    @Override
    public Vector<Answer> getQuestions(int id) throws RemoteException {

        Vector<Answer> questions = new Vector<>();

        try {
            PostgresConnector pc = new PostgresConnector(this.host, this.database, this.user, this.psw);

            pc.connect();
            String query = "SELECT * from questions where surveyid = " + id + ";";

            Statement state = pc.getStatement();

            ResultSet result = state.executeQuery(query);

            while (result.next()) {

                questions.add(new Answer(result.getInt("id"), result.getInt("surveyid"), result.getString("question")));
            }

            result.close();
            state.close();
            pc.disconnect();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return questions;
    }

}
