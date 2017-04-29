import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Date;

public class TicketsGUI extends JFrame {
    private JPanel rootPanel;
    private JButton addTicketBtn;
    private JButton searchBtn;
    private JButton quitBtn;
    private JButton resolveBtn;
    private JButton showAllTicketsBtn;
    private JButton showAllResolvedBtn;
    private JTextField searchTxt;
    private JTextField reportTxt;
    private JTextField issueTxt;
    private JTextField resolutionTxt;
    private JComboBox priorityCombo;
    private JComboBox ticketIDCombo;
    private JList resultList;

    private DefaultListModel<Ticket> listModel;

    static LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();
    static LinkedList<Ticket> resolvedTicket = new LinkedList<>();

    BufferedWriter openWriter = new BufferedWriter(new FileWriter("open_tix.txt", true));
    Scanner fileInput = new Scanner(new File("open_tix.txt"));

    String dataForm = "EEE MMM dd hh:mm:ss z yyyy";
    SimpleDateFormat format = new SimpleDateFormat(dataForm);

    public TicketsGUI() throws Exception {

        super("Ticket Management System");

        try {

            while (fileInput.hasNext()) {

                Ticket openTicket = new Ticket(fileInput.nextLine(),
                        Integer.parseInt(fileInput.nextLine().substring(10)),
                        fileInput.nextLine(),
                        format.parse(fileInput.nextLine().substring(15)),
                        null, null);
                ticketQueue.add(openTicket);
            }
        } catch
                (NumberFormatException ex) {
            System.out.println("Error");
        }

        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        listModel = new DefaultListModel<Ticket>();
        resultList.setModel(listModel);

        for (Ticket tix : ticketQueue) {

            listModel.addElement(tix);
            ticketIDCombo.addItem(tix.getTicketID());
        }

        listeners();
        pack();
        setSize(1000, 450);
        setVisible(true);

    priorityCombo.addItem(1);
    priorityCombo.addItem(2);
    priorityCombo.addItem(3);
    priorityCombo.addItem(4);
    priorityCombo.addItem(5);
    }

public static int getCurrentID() {

    int id = 1;

    for (Ticket ticket : ticketQueue) {

        if (ticket.getTicketID() > id) {

            id = ticket.getTicketID();
        }
    }

    id += 1;
    return id;
}

public void listeners() {

    addTicketBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String report = reportTxt.getText();
            String issue = issueTxt.getText();
            int priority = (Integer)priorityCombo.getSelectedItem();
            Date date = new Date();

            Ticket tix = new Ticket(issue, priority, report, date, null, null);
            priorityOrder(ticketQueue, tix);
            listModel.addElement(tix);
            ticketIDCombo.addItem(tix.getTicketID());
        }
    });

    searchBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            listModel.clear();
            String search = searchTxt.getText();

            for (Ticket ticket : ticketQueue) {

                if (ticket.getDescription().equalsIgnoreCase(search)) {

                    listModel.addElement(ticket);

                }
            }
        }
    });

    showAllTicketsBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            listModel.clear();

            for (Ticket ticket : ticketQueue) {

                listModel.addElement(ticket);
            }
        }
    });

    showAllResolvedBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            listModel.clear();

            for (Ticket ticket : resolvedTicket) {

                listModel.addElement(ticket);
            }
        }
    });

    resolveBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {


            int ID = (Integer)ticketIDCombo.getSelectedItem();
            String resolve = resolutionTxt.getText();
            Date today = new Date();

            for (Ticket ticket : ticketQueue) {

                if (ticket.getTicketID() == ID) {

                    ticket.setFixDescription(resolve);
                    ticket.setDateResolved(today);
                    resolvedTicket.add(ticket);
                    ticketQueue.remove(ticket);
                    listModel.removeElement(ticket);
                    ticketIDCombo.removeItem(ticket.ticketID);
                    break;
                }
            }
        }
    });

    quitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                BufferedWriter quitWrite = new BufferedWriter(new FileWriter("opentix.txt"));

                for (Ticket tix : ticketQueue) {

                    quitWrite.write(tix.getDescription() + "\nPriority: " + tix.getPriority() + "\n" +
                            tix.getReporter() + "\nDate reported: " + tix.getDateReported() + "\n");
                }

                    String date = new SimpleDateFormat("MMM_dd_yyyy").format(new Date());
                    String closeTix = "Resolved_tickets_as_of_" + date + ".txt";

                    BufferedWriter resolveWrite = new BufferedWriter(new FileWriter(closeTix));

                    for (Ticket tix : resolvedTicket) {

                        resolveWrite.write("Issue: " + tix.getDescription() + "\nPriority: " +
                        tix.getPriority() + "\nReporter: " + tix.getReporter() + "\nDate reported: " +
                        tix.getDateReported() + "\nDate resolved: " + tix.getDateResolved() +
                        "\nResolution: " + tix.getFixDescription() + "\n");
                    }

                    quitWrite.close();
                    resolveWrite.close();
                } catch (IOException ioe) {

                    JOptionPane.showMessageDialog(TicketsGUI.this, "Error updating 'open_tix.txt'" + "Try Again.");
                }

                System.exit(0);
            }
    });
}

public void priorityOrder(LinkedList<Ticket> tickets, Ticket ticket) {

    int ticketPriority = ticket.getPriority();

    for (int x = 0; x < tickets.size(); x++) {

        if (ticketPriority >= tickets.get(x).getPriority()) {

            tickets.add(x, ticket);
            return;
          }
      }

    tickets.addLast(ticket);
    }
}