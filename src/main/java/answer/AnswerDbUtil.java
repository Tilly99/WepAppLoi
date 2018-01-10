package answer;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * <code>AnswerDbUtil</code> Reads answers and total of votes from the database
 * and writes the selected vote to the database. Using JDBC to Connect Tomcat to
 * MySQL This means that the resource is in the Context element in
 * META-INF/context.xml. This will direct Tomcat to create the resource when the
 * application launches. The resource reference is in the "WEB-INF/web.xml"
 * file. This provides a references to the database The JDBC ‐ drivers of MySQL
 * should be in the project libraries so there can be a database connection.
 *
 * @author Tilly Koot
 * @version 1.0 5‐01‐2018
 */
public class AnswerDbUtil {

    // Define datasource/connection pool for Resource Injection by Tomcat Server
    private final DataSource dataSource;

    /**
     * <code>AnswerDbUtil</code>Class constructor specifying number of objects
     * to create.
     *
     * @param theDataSource datasource/connection pool for Resource Injection by
     * Tomcat Serve
     */
    public AnswerDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    /**
     * <code>getAnswer</code> Create connection to the database, preform query
     * and adds id, answer, vote and total to list and close the connection
     *
     * @param myConn database connection
     * @param myStmt Statement
     * @param myRs ResultSet
     * @param sql string with query
     * @param totalVotes integer Total votes from database
     * @param id integer for id answer from database
     * @param answer string for answer from database
     * @param vote integer for vote per answer
     * @param total integer for % votes per answer
     *
     * @return answer
     */
    List<Answer> getAnswer() throws Exception {
        List<Answer> answers = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        // retrieve total votes from database
        int totalVotes = getTotalVote();

        try {
            // get a connection
            myConn = dataSource.getConnection();

            // create a sql statement
            String sql = "select * from polls order by id";
            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // procces result set
            while (myRs.next()) {
                // Retreive data from result set row
                int id = myRs.getInt("id");
                String answer = myRs.getString("answer");
                int polls = myRs.getInt("polls");
                int total = (polls * 100) / totalVotes;
                // create new answer object
                Answer tempAnswer = new Answer(id, answer, polls, total);

                // add it to the list of answers
                answers.add(tempAnswer);
            }
            return answers;

        } finally {
            // clean up JDBC object
            close(myConn, myStmt, myRs);
        }
    }

    /**
     * <code>updateQuestion</code> Updates vote of selected answer in jsp form
     * in database
     *
     * @param myConn database connection
     * @param myStmt PreparedStatement
     * @param sql string with query
     * @param id integer for id answer from database
     *
     * @return answer
     */
    void updateQuestion(int id) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            // get a connection
            myConn = dataSource.getConnection();

            // update sql for insert
            String sql = "update polls SET polls=polls + 1 where id=?";

            // convert id to integer
            //int pollId = Integer.parseInt(id);
            // Prepare statement    
            myStmt = myConn.prepareStatement(sql);

            // set the param values for answer id
            myStmt.setInt(1, id);

            // execute query
            myStmt.execute();

        } catch (SQLException sql) {
            out.println(sql);
        } finally {
            // clean up JDBC object
            close(myConn, myStmt, null);
        }
    }

    /**
     * <code>getTotalVote</code> Gets total of votes from database
     *
     * @return totalVotes
     * @throws java.lang.Exception
     */
    public int getTotalVote() throws Exception {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        int totalVotes = 0;

        try {
            // get a connection
            myConn = dataSource.getConnection();

            // create a sql statement
            String sql = "SELECT SUM(polls) FROM polls";
            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // Retrieve data from result set row            
            while (myRs.next()) {
                totalVotes = myRs.getInt(1);
            }

            return totalVotes;
        } finally {
            // clean up JDBC object
            close(myConn, myStmt, myRs);
        }
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

        try {
            if (myRs != null) {
                myRs.close();
            }
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();   // doesn't really close it ... just puts back in connection pool
            }
        } catch (SQLException exc) {
            out.println("Geen db connection");
        }
    }
}
