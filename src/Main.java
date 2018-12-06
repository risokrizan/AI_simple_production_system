import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    Gui gui;
    FactsAndRelations fact;
    int ruleState = 0;
    ArrayList<Rule> rules = new ArrayList<Rule>();
    HashMap<String, Person> persons = new HashMap<String, Person>();
    ArrayList<Combinations> trioPerson = new ArrayList<Combinations>();
    ArrayList<Combinations> quatroPerson = new ArrayList<Combinations>();

    public Main(Gui gui) {
        this.gui = gui;
        fact = new FactsAndRelations(persons);
        readFromFile();
        createCombinations();
        gui.printRules(rules);
        gui.printFacts(persons);
    }

    private void createCombinations() {
        ArrayList keys = new ArrayList(persons.keySet());

        for (int i = 0; i < keys.size(); i++) {
            for (int j = 0; j < keys.size(); j++) {
                for (int k = 0; k < keys.size(); k++) {
                    Person p1 = persons.get(keys.get(i));
                    Person p2 = persons.get(keys.get(j));
                    Person p3 = persons.get(keys.get(k));

                    if (p1 == p2 || p1 == p3 || p2 == p3) {

                    } else {

                        trioPerson.add(new Combinations(p1, p2, p3));
                    }
                }
            }
        }

        for (int i = 0; i < keys.size(); i++) {
            for (int j = 0; j < keys.size(); j++) {
                for (int k = 0; k < keys.size(); k++) {
                    for (int l = 0; l < keys.size(); l++) {
                        Person p1 = persons.get(keys.get(i));
                        Person p2 = persons.get(keys.get(j));
                        Person p3 = persons.get(keys.get(k));
                        Person p4 = persons.get(keys.get(l));

                        if (p1 == p2 || p1 == p3 || p1 == p4 || p2 == p3 || p2 == p4 || p3 == p4) {

                        } else {

                            quatroPerson.add(new Combinations(p1, p2, p3, p4));
                        }
                    }
                }
            }
        }
    }

    /**
     * prejdi vsetky fakty pre pravidlo v poradi
     */

    public void doForOneRule() {
        if (ruleState < rules.size()) {

            if(rules.get(ruleState).condition.vars.size() <= 3) {
                for (Combinations c : trioPerson) {
                    testCombination(c, rules.get(ruleState));
                }
            }else {
                for (Combinations c : quatroPerson) {
                    testCombination(c, rules.get(ruleState));
                }
            }
            gui.printFacts(persons);
            ruleState++;
        }
    }

    /**
     * prejdi vsetky pravidla pre vsetky fakty
     * nove fakty sa generuju v priebehu
     */

    public void doForAllRules() {
        for (Rule r : rules) {
            if(r.condition.vars.size() <= 3) {
                for (Combinations c : trioPerson) {
                    testCombination(c, r);
                }
            }else {
                for (Combinations c : quatroPerson) {
                    testCombination(c, r);
                }
            }
            gui.printFacts(persons);
        }
    }


    private void testCombination(Combinations c, Rule r) {
        String testResult = r.testTrioForRule(c);
        if (!(testResult == null)) {
            gui.printHelpInput(testResult);

            for (int i = 0; i < r.actionByRule.aVarCondList.size(); i++) {
                String actionMessage = r.executeAction(c, i);

                if (actionMessage.startsWith("pridaj")) {
                    gui.printHelpInput(actionMessage);
                } else if (actionMessage.startsWith("vymaz")) {
                    gui.printHelpInput(actionMessage);
                } else if (actionMessage.startsWith("sprava")) {
                    gui.printMessage(actionMessage);
                }
            }
        }
    }


    /**
     * nacitaj z spolocneho subora
     * vstupne fakty
     * pravidla
     */
    private void readFromFile() {
        try {
            File file = new File("/Users/RisoKrizan/IdeaProjects/UI_zad4/vstupy/vstup2.txt");
            Scanner sc = new Scanner(file);

            /*pridaj fakty vo faze rozparsovania do hashmapy*/
            while (!sc.hasNext("###")) {

                String lineOfFact = sc.nextLine();
                fact.parseFacts(lineOfFact);

            }

            /*pridaj vztahy do arraylistu*/
            while (sc.hasNextLine()) {

                if (sc.hasNext("###")) {
                    sc.nextLine();
                    String name = sc.nextLine();

                    if (sc.hasNext("@@@")) {
                        sc.nextLine();
                        String condition = sc.nextLine();

                        if (sc.hasNext("===")) {
                            sc.nextLine();
                            String action = sc.nextLine();
                            Rule rule = new Rule(name, condition, action);
                            rules.add(rule);
                        }
                    }

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
