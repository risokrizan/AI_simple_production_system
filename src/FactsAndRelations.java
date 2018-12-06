import java.util.Arrays;
import java.util.HashMap;


public class FactsAndRelations {

    private HashMap<String, Person> personFacts;

    public FactsAndRelations(HashMap<String, Person> personalyFact) {
        personFacts = personalyFact;
    }

    /**
     * pridaj fakt k osobe s menom
     * vytvorenie osoby podla potreby
     *
     * @param mainPersonName
     * @param fact
     */
    private void addNewFact(String mainPersonName, String fact) {
        Person p;

        if (personFacts.containsKey(mainPersonName)) {
            p = personFacts.get(mainPersonName);
        } else {
            p = new Person(mainPersonName);
        }

        p.addFact(fact);
        personFacts.put(p.getNameOfPerson(), p);
    }

    /**
     * pridaj vztah medzi dvomi osobami
     * vytvaraju sa instancie osob podla toho ci existuju uz alebo nie
     *
     * @param mainPersonName
     * @param relationWithParsonName
     * @param relation
     */
    private void addNewRelation(String mainPersonName, String relationWithParsonName, String relation, boolean doubleAddTwisted) {
        Person p;
        Person pRelationWith;

        if (personFacts.containsKey(mainPersonName)) {
            p = personFacts.get(mainPersonName);
        } else {
            p = new Person(mainPersonName);
        }

        if (personFacts.containsKey(relationWithParsonName)) {
            pRelationWith = personFacts.get(relationWithParsonName);
        } else {
            pRelationWith = new Person(relationWithParsonName);
        }

        if(doubleAddTwisted) {
            pRelationWith.addRelationship(relation, p);
        }

        p.addRelationship(relation, pRelationWith);
        personFacts.put(p.getNameOfPerson(), p);
        personFacts.put(pRelationWith.getNameOfPerson(), pRelationWith);
        //pRelationWith.addRelationship(relation, p);

    }

    /**
     * rozsplitovanie riadku na mena, potrebne fakty a vztahy
     * vystupom su stringy z ktorych sa vytvara fakt pre osobu s menom
     *
     * @param lineOfFact
     */
    public void parseFacts(String lineOfFact) {

        boolean activeRelations = false;
        boolean activeAddDoubleRelations = false;
        String mainPersonName = "";
        String secondPersonName = null;
        StringBuilder factOrRelationString = new StringBuilder("");

        String[] infoFromFacts = lineOfFact.split("\\s+");

        for (String infString : infoFromFacts) {
            if (Character.isUpperCase(infString.charAt(0))) {

                if (mainPersonName.isEmpty()) {
                    mainPersonName = infString;

                    int decision = Arrays.asList(infoFromFacts).indexOf(infString);
                    if (decision > 0) {
                       activeAddDoubleRelations = true;
                    }
                } else {
                    secondPersonName = infString;
                    activeRelations = true;
                }
            } else {

                factOrRelationString.append(infString + " ");
            }

        }

        if (!activeRelations) {
            addNewFact(mainPersonName.toString(), factOrRelationString.toString());
        } else {
            addNewRelation(mainPersonName.toString(), secondPersonName.toString(), factOrRelationString.toString(), activeAddDoubleRelations);
        }
    }
}
