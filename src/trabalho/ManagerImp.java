/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private PostgresConnector pc;

    public ManagerImp(String host, String user, String db, String pw) throws RemoteException, Exception {
        super();

        this.pc = new PostgresConnector(host, db, user, pw);
        this.pc.connect();
    }

    @Override
    public void insertSurvey(Survey s) throws RemoteException {

        try {

            this.semaphore.acquire();

            String query = "INSERT INTO surveys (numans) VALUES (0) RETURNING id;";

            Statement state = this.pc.getStatement();

            ResultSet set = state.executeQuery(query);

            set.next();

            int id = set.getInt(1);

            for (int i = 0; i < s.num_quests; i++) {
                String update = "INSERT INTO questions (surveyid, question) VALUES (" + id + "," + "'" + s.questions[i] + "'" + ");";

                state.executeUpdate(update);
            }

            set.close();

            this.semaphore.release();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Vector<Integer> consultSurvey() throws RemoteException {

        Vector<Integer> result = new Vector<>();

        try {

            Statement state = this.pc.getStatement();
            try {
                ResultSet rs = state.executeQuery("SELECT * from surveys;");

                while (rs.next()) {
                    int id = rs.getInt("id");

                    result.add(id);
                }
                rs.close();

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Problems retrieving data from db...");
            }

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public int deleteSurvey(int id) throws RemoteException {
        
        int deleted = 0;
        
        try {

            this.semaphore.acquire();

            String query = "DELETE from surveys where id = " + id + ";";

            Statement state = this.pc.getStatement();

            deleted = state.executeUpdate(query);

            this.semaphore.release();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deleted;
    }

    @Override
    public Questions questionsSurvey(int id) throws RemoteException {

        Vector<String> questions = new Vector<>();
        int i = 0;

        try {

            String query = "select question from questions where surveyid = " + id + ";";

            Statement state = this.pc.getStatement();

            ResultSet result = state.executeQuery(query);

            while (result.next()) {

                questions.add(result.getString("question"));
                i++;
            }

            result.close();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new Questions(i, questions);
    }

    @Override
    public int answersSurvey(Vector<Answer> a, int id) throws RemoteException {

        int submissionCode = -1;

        try {

            String query = "INSERT INTO submission_codes DEFAULT VALUES RETURNING codesub;";

            Statement state = this.pc.getStatement();
            ResultSet result = state.executeQuery(query);
            result.next();

            submissionCode = result.getInt(1);

            this.semaphore.acquire();

            for (Answer ans : a) {

                query = "INSERT INTO answers (codsub, questionid, surveyid, value) VALUES (" + submissionCode
                        + "," + ans.getId() + "," + ans.getSurveyId() + "," + ans.getAnswer() + ");";

                state.executeUpdate(query);
            }

            query = "UPDATE surveys SET numans = numans + 1 WHERE id = " + id + ";";

            state.executeUpdate(query);

            this.semaphore.release();

        } catch (SQLException | InterruptedException ex) {
            Logger.getLogger(ManagerImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return submissionCode;

    }

    @Override
    public int consult_numbers_answers(int id) throws RemoteException {

        int numAns = -1;

        try {

            String query = "SELECT numans from surveys where id = " + id + ";";

            Statement state = this.pc.getStatement();

            ResultSet result = state.executeQuery(query);

            if(result.next());

                numAns = result.getInt("numans");

            result.close();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return numAns;
    }

    @Override
    public Vector<QuestionAvg> average_Survey(int id) throws RemoteException {

        Vector<QuestionAvg> quest = new Vector<>();

        try {

            String query = "SELECT questions.question as quest, questions.surveyid as sid, avg(answers.value) AS avg_value FROM "
                    + "answers inner join questions on answers.questionid = questions.id "
                    + "GROUP BY questions.question, questions.surveyid HAVING questions.surveyid = " + id + ";";

            Statement state = this.pc.getStatement();

            try (ResultSet result = state.executeQuery(query)) {

                while (result.next()) {

                    quest.add(new QuestionAvg(result.getString("quest"), result.getFloat("avg_value")));

                }

            }

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return quest;
    }

    @Override
    public Vector<Answer> getQuestions(int id) throws RemoteException {

        Vector<Answer> questions = new Vector<>();

        try {

            String query = "SELECT * from questions where surveyid = " + id + ";";

            Statement state = this.pc.getStatement();

            ResultSet result = state.executeQuery(query);

            while (result.next()) {

                questions.add(new Answer(result.getInt("id"), result.getInt("surveyid"), result.getString("question")));
            }

            result.close();

        } catch (Exception ex) {
            Logger.getLogger(ManagerImp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return questions;
    }

    @Override
    public void disconnectDB() throws RemoteException {

        this.pc.disconnect();
    }
}
