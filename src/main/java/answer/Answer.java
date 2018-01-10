package answer;

/**
 * <code>Answer</code> To list the answers id, answer, poll and total from and
 * to the database for display in jsp files.
 *
 * @author Tilly Koot
 * @version 1.0 5‐01‐2018
 */
public class Answer {

    private int id;
    private String answer;
    private int poll;
    private int total;

    /**
     * Class constructor specifying number of objects to create.
     *
     * @param id integer id from answer in database
     * @param answer string answer from database
     * @param poll string vote from answer
     * @param total total number of vote
     */
    public Answer(int id, String answer, int poll, int total) {
        this.id = id;
        this.answer = answer;
        this.poll = poll;
        this.total = total;
    }

    /**
     * This is the get-methode for id from answer.
     *
     * @return returns the id
     */
    public int getId() {
        return id;
    }

    /**
     * This is the set-methode for id from answer.
     *
     * @param id integer for id from answer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This is the get-methode for answer.
     *
     * @return returns the answer
     */
    public String getQuestion() {
        return answer;
    }

    /**
     * This is the set-methode for answer.
     *
     * @param answer string for answer
     */
    public void setQuestion(String answer) {
        this.answer = answer;
    }

    /**
     * This is the get-methode for total of votes
     *
     * @return returns the total of votes
     */
    public int getTotal() {
        return total;
    }

    /**
     * This is the get-methode for total of votes.
     *
     * @param total integer for total of votes.
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * This is the get-methode for vote.
     *
     * @return vote
     */
    public int getPoll() {
        return poll;
    }

    /**
     * This is the set-methode for vote
     *
     * @param poll for vote
     */
    public void setPoll(int poll) {
        this.poll = poll;
    }

}
