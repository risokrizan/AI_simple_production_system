import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


public class Gui {


    private JFrame mainFrame;
    private JTextArea factsTextArea;
    private JTextArea baseRulesTextArea;
    private JTextArea messageInputTextArea;
    private JTextArea helpInputTextArea;
    private JButton vykonajJedenKrokButton;
    private JButton vykonajDoKoncaButton;
    private JPanel mainPane;
    private JLabel messageLabel;
    private JLabel helpInputLabel;
    private JLabel rulesLabel;
    private JLabel workLabel;


    private Main main;

    private Gui() {
        setMainFrame(createFrame());
        actionListners();
    }

    public static void main(String args[]) {

        Gui gui = new Gui();
        gui.main = new Main(gui);

    }

    private void actionListners() {
        vykonajDoKoncaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.doForAllRules();
            }
        });
        vykonajJedenKrokButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.doForOneRule();
            }
        });
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("PRODUKCNY SYSTEM");
        frame.setSize(800, 900);
        frame.setContentPane(getMainPane());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;

    }

    private JPanel getMainPane() {
        return mainPane;
    }

    private void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }


    void printRules(ArrayList<Rule> rules) {
        for(Rule r : rules){
           baseRulesTextArea.append(r.toString());
           baseRulesTextArea.append("\n");
        }
    }

    void printFacts(HashMap<String, Person> facts) {
        factsTextArea.setText("");

        for(Person p : facts.values()){
            factsTextArea.append(p.printAssignedFacts());
        }
    }

    void printHelpInput(String s) {
        helpInputTextArea.append(s + "\n");
    }

    void printMessage(String actionMessage) {
        messageInputTextArea.append(actionMessage + "\n");
    }
}
