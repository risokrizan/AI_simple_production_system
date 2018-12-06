import java.util.*;


public class Person {

    private String nameOfPerson;
    private HashMap<String, ArrayList<Person>> relationships = new HashMap<String, ArrayList<Person>>();
    private HashMap<String, Boolean> facts = new HashMap<String, Boolean>();

    public Person(String name) {
        nameOfPerson = name;
    }


    /**
     * pridanie faktu osobe
     *
     * @param factName
     * @return
     */
    public boolean addFact(String factName) {

        if (!facts.containsKey(factName)) {
            facts.put(factName, true);
            return true;
        }

        return false;
    }

    /**
     * zmaz fakt osobe
     *
     * @param factName
     * @return
     */
    public boolean removeFact(String factName) {

        if (facts.containsKey(factName)) {
            facts.remove(factName);
            return true;
        }

        return false;
    }

    /**
     * overuje ci sa fakt nachadza u cloveka
     *
     * @param relation
     * @return
     */
    public boolean checkFact(String relation) {

        if (facts.containsKey(relation)) {
            return true;
        }

        return false;
    }

    /**
     * pridaj vztah osoe
     *
     * @param relationshipName
     * @param person
     */
    public void addRelationship(String relationshipName, Person person) {

        List<Person> personsWithRelationShip = relationships.get(relationshipName);

        if (personsWithRelationShip == null) {
            personsWithRelationShip = new ArrayList<Person>();
            relationships.put(relationshipName, (ArrayList<Person>) personsWithRelationShip);
        }

        personsWithRelationShip.add(person);
        removeRepeatedPersonsInRelation(personsWithRelationShip);


    }

    private void removeRepeatedPersonsInRelation(List<Person> personsWithRelationShip) {
        Set<Person> removedClones = new HashSet<>();
        removedClones.addAll(personsWithRelationShip);
        personsWithRelationShip.clear();
        personsWithRelationShip.addAll(removedClones);
    }

    /*vymaz vztah*/
    public void removeRelationship(String action, Person person) {

        List<Person> pRelationship = relationships.get(action);

        for (Iterator<Person> it = pRelationship.iterator(); it.hasNext(); ) {
            Person p = it.next();
            if (p != person) {
                it.remove();
            }
        }
    }

    /**
     * najdi vztah k druhej osobe
     *
     * @param relation
     * @param person2
     * @return
     */
    public boolean checkRelationShipWith(String relation, Person person2) {

        if (relationships.containsKey(relation)) {
            ArrayList<Person> pList = relationships.get(relation);

            for (Person p : pList) {
                if (p == person2) {
                    return true;
                }
            }

        }
        if (relation.startsWith("<>") && this != person2) {
            return true;
        }


        return false;
    }


    public String getNameOfPerson() {
        return nameOfPerson;
    }

    /**
     * vytvor string z zozbieranych faktov
     *
     * @return
     */
    public String printAssignedFacts() {

        StringBuilder allFactsForPerson = new StringBuilder("");

        for (Map.Entry<String, ArrayList<Person>> entry : relationships.entrySet()) {

            String key = entry.getKey();
            ArrayList<Person> person = entry.getValue();

            for (Person p : person) {

                allFactsForPerson.append(this.getNameOfPerson() + " " + key + p.getNameOfPerson() + "\n");

            }
        }

        for (String factString : facts.keySet()) {
            allFactsForPerson.append(this.getNameOfPerson() + " " + factString + "\n");
        }

        return allFactsForPerson.toString();
    }

}
