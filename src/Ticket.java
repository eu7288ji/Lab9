import java.util.Date;

public class Ticket {

    private int priority;
    private String reporter;
    private String description;
    private String fixDescription;
    private Date dateReported;
    private Date dateResolved;

    private static int ticketIDCounter = TicketsGUI.getCurrentID();

    protected int ticketID;

    public Ticket(String desc, int pri, String rep, Date date, String fix, Date res) {

        this.description = desc;
        this.priority = pri;
        this.reporter = rep;
        this.dateReported = date;
        this.dateResolved = res;
        this.fixDescription = fix;
        this.ticketID = ticketIDCounter;
        ticketIDCounter++;
    }

    public int getPriority() {
        return priority;
    }

    public String getReporter() {
        return reporter;
    }

    public String getDescription() {
        return description;
    }

    public String getFixDescription() {
        return fixDescription;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public Date getDateResolved() {
        return dateResolved;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setFixDescription(String fixDescription) {
        this.fixDescription = fixDescription;
    }

    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String toString() {

        return("ID: " +
                this.ticketID + " Issue: " +
                this.description + " Priority: " +
                this.priority + " Reported by: " +
                this.reporter + " Reported on: " +
                this.dateReported + " Resolved on: " +
                this.dateResolved + " Resolution: " +
                this.fixDescription);
    }
}
