package answer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * <code>AnswerServlet</code>This servlet is using JDBC to Connect Tomcat to
 * MySQL This means that the resource is in the Context element in
 * META-INF/context.xml. This will direct Tomcat to create the resource when the
 * application launches. The resource reference is in the \"WEB-INF/web.xml\"
 * file. This provides a references to the database.The jsp files and the
 * servlet use the standard JSP tags from Orcal. Servlet does the business
 * logic. JSP handles the presentation view. The JSTL files should be in the
 * project libraties so the jsp files can use the tags.
 *
 * @author Tilly Koot
 * @version 1.0 5‐01‐2018
 */
@WebServlet(name = "AnswerServlet", urlPatterns = {"/AnswerServlet"})
public class AnswerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AnswerDbUtil answerDbUtil;

    // Define datasource/connection pool for Resource Injection by Tomcat Server
    @Resource(name = "jdbc/poll")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.

        // create our answer db util ..... pass in the conn pool / datasource
        try {
            answerDbUtil = new AnswerDbUtil(dataSource);

        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method. Reads the list with answers
     * from the database into the index.jsp.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get the id and answerg from the database util.
        List<Answer> answers = null;
        try {
            answers = answerDbUtil.getAnswer();
        } catch (Exception ex) {
            Logger.getLogger(AnswerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // add answers to the request
        request.setAttribute("ANSWER_LIST", answers);

        // send list to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the * HTTP <code>POST</code> method. Update vote from selected
     * answer into database, reads the list with answers and total votes from
     * database into adds the input to request.SetAttribute for use in
     * result.jsp loads result.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // add checked radiobutton from form data (=equal to id answer) to integer
        int id = Integer.parseInt(request.getParameter("Vote"));

        try {
            // preform update on database
            answerDbUtil.updateQuestion(id);
        } catch (Exception ex) {
            Logger.getLogger(AnswerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // get total votes from datatbase
        int totalVotes = 0;
        try {
            totalVotes = answerDbUtil.getTotalVote();
        } catch (Exception ex) {
            Logger.getLogger(AnswerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // get the id, answer, votes and percentage votes from the database util.
        List<Answer> answers = null;
        try {
            answers = answerDbUtil.getAnswer();
        } catch (Exception ex) {
            Logger.getLogger(AnswerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // add list answers to the request
        request.setAttribute("ANSWER_LIST", answers);
        // add total votes to the request to display the total number of votes.       
        request.setAttribute("TOTALVOTES", totalVotes);
        // add checked radiobutton to the request to highlight the selected radiobutton
        request.setAttribute("ID", id);

        // send list to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Reads answers from database into the index.jsp and the response is to\n"
                + "result.jsp with the same list of answers, the selected answer and total votes.\n"
                + "The servlet and the JSP are integrate together (MVC).\n"
                + "Servlet does the business logic. JSP handles the presentation view\n"
                + "This servlet is using JDBC to Connect Tomcat to MySQL\n"
                + "This means that the resource is in the Context element\n"
                + "in META-INF/context.xml."
                + "The jsp files and the servlet use the standard JSP tags\n"
                + "from Orcal. Servlet does the business logic. JSP handles the\n"
                + "presentation view. The JSTL files should be in the project\n"
                + "libraties so the jsp files can use the tags.";
    }// </editor-fold>
}
