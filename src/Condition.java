import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Condition {

    private int numberOfPerson;

    ArrayList<Entry> aVarCondList = null;
    public HashMap<Character, Integer> vars = null;

    class Entry{
        String relations;
        ArrayList<Integer> varsValues;
        String action;
    }

    public Condition(String[] cond, Condition decision){

        vars = createValFromCond(cond, decision);
        aVarCondList = createVarCondListByVar(vars, cond, decision);
        numberOfPerson = 0;
    }

    /*
    * zoberie mapu premenych a vrati arraylist Entry
    * ktore predstavuju podmienky
    * */
    private ArrayList<Entry> createVarCondListByVar(HashMap<Character, Integer> vars, String[] cond, Condition decision){
        ArrayList<Entry> varCoditions = new ArrayList<Entry>();

        for(String c : cond) {

            String action = null;
            String[] infoFromConds = c.split("\\s+");
            ArrayList<Integer> varsValues = new ArrayList<Integer>();
            StringBuilder sbRealtion = new StringBuilder();

            if(decision != null){
                action = c.split("\\s+")[0];
                String[] copyOfArrayInfo = Arrays.copyOfRange(infoFromConds, 1, infoFromConds.length);

                for (String v : copyOfArrayInfo) {

                    if (v.length() > 1 && Character.isUpperCase(v.charAt(1))) {
                        if(decision.vars.containsKey(v.charAt(1))){
                            varsValues.add(decision.vars.get(v.charAt(1)));
                        }
                    } else {

                        sbRealtion.append(v + " ");

                    }
                }
                //infoFromConds = Arrays.copyOfRange(copyOfArrayInfo, 0, copyOfArrayInfo.length);
            }else {


                for (String v : infoFromConds) {

                    if (v.length() > 1 && Character.isUpperCase(v.charAt(1))) {
                        if (vars.containsKey(v.charAt(1))) {
                            varsValues.add(vars.get(v.charAt(1)));
                        }
                    } else {

                        sbRealtion.append(v + " ");

                    }

                }
            }
            Entry e = new Entry();
            e.relations = sbRealtion.toString();
            e.varsValues = varsValues;
            e.action = action;
            varCoditions.add(e);
        }

        return varCoditions;
    }

    /**
     * zoberie pole stringov zo suboru
     * vrati mapu kde kluc je premena a hodnota je cislo ktore sa pouziva v entry
     * @param cond
     * @return
     */

    private HashMap<Character, Integer> createValFromCond(String[] cond, Condition decision){
        HashMap<Character, Integer> vars = new HashMap<Character, Integer>();
        int i = 0;
        for(String c : cond) {

            String[] infoFromConds = c.split("\\s+");


            for (String v : infoFromConds){

                if(decision != null){
                    if (v.length() > 1 && Character.isUpperCase(v.charAt(1))) {
                        if(decision.vars.containsKey(v.charAt(1))){
                            vars.put(v.charAt(1), decision.vars.get(v.charAt(1)));
                        }
                    }
                }else {

                    if (v.length() > 1 && Character.isUpperCase(v.charAt(1))) {
                        if (vars.containsKey(v.charAt(1))) {
                        } else {
                            vars.put(v.charAt(1), i++);
                        }
                    }
                }
            }
        }
        numberOfPerson = i;
        return vars;
    }

    public boolean matchCondition(ArrayList<Person> person){

        for (Entry e: aVarCondList) {
            if(!checkOneCond(e, person)){
                return false;
            }
        }

        return true;

    }

    private boolean checkOneCond(Entry aVarCond, ArrayList<Person> person){

        String relation = aVarCond.relations;
        ArrayList<Integer> varsForPersons = aVarCond.varsValues;

        if(varsForPersons.size() > 1){
            Person person1 = person.get(varsForPersons.get(0));
            Person person2 = person.get(varsForPersons.get(1));

            return person1.checkRelationShipWith(relation, person2);

        }else{
            Person person1 = person.get(varsForPersons.get(0));

            return person1.checkFact(relation);

        }

    }


    public ArrayList<Entry> getaVarCondList() {
        return aVarCondList;
    }

}
