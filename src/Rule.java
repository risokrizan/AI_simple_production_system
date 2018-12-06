import java.util.ArrayList;


public class Rule {

    private String nameOfRule;
    private String condActOfRule;
    Condition condition;
    Condition actionByRule;


    public Rule(String name, String condition, String action) {

        this.nameOfRule = name;
        setCondActOfRule(condition, action);
        this.condition = parseCondition(condition);
        this.actionByRule = parseAction(action);

    }

    /**
     * otestuj ci sa da doplnit trojca do vztahu
     * vytvor string pre doplneny vztah
     *
     * @param c
     * @return
     */
    public String testTrioForRule(Combinations c) {

        if (condition.matchCondition(c.getTrioComb())) {

            ArrayList<Condition.Entry> eList = condition.getaVarCondList();
            StringBuilder sbPrint = new StringBuilder();

            sbPrint.append(this.nameOfRule + ": ");

            for (Condition.Entry e : eList) {
                if (e.varsValues.size() > 1) {

                    Person p1 = c.elementOnIndex(e.varsValues.get(0));
                    Person p2 = c.elementOnIndex(e.varsValues.get(1));

                    p1.addRelationship(e.relations, p2);

                    sbPrint.append(p1.getNameOfPerson()  + " " +  e.relations  + " " +
                            p2.getNameOfPerson() + ", ");
                } else {

                    Person p1 = c.elementOnIndex(e.varsValues.get(0));
                    p1.addFact(e.relations);

                    sbPrint.append(p1.getNameOfPerson() + " " + e.relations+ ", ");
                }
            }

            sbPrint.delete(sbPrint.length()-2, sbPrint.length());

            return sbPrint.toString();
        }
        return null;
    }

    public String executeAction(Combinations cList, int index) {
        
        ArrayList<Condition.Entry> actionList = actionByRule.getaVarCondList();

        Condition.Entry e = actionByRule.getaVarCondList().get(index);

            switch (e.action) {
                case "pridaj":
                    return addByAction(e, cList);
                case "vymaz":
                    return deleteByAction(e, cList);
                case "sprava":
                    return messageByAction(e, cList);
                default:
                    System.out.println("ACTION ERROR");
                    break;
            }
        return null;
    }

    private String messageByAction(Condition.Entry e, Combinations cList) {

        StringBuilder messageSb = new StringBuilder("sprava : ");
        if (e.varsValues.size() > 1) {
            Person p1 = cList.elementOnIndex(e.varsValues.get(0));
            Person p2 = cList.elementOnIndex(e.varsValues.get(1));

            messageSb.append(p1.getNameOfPerson() + " " + e.relations + " " + p2.getNameOfPerson());

        }else{
            Person p1 = cList.elementOnIndex(e.varsValues.get(0));

            messageSb.append(p1.getNameOfPerson() + " " + e.relations);
        }

        return  messageSb.toString();
    }

    private String deleteByAction(Condition.Entry e, Combinations cList) {

        StringBuilder messageSb = new StringBuilder("vymaz : ");

        if (e.varsValues.size() > 1) {
            Person p1 = cList.elementOnIndex(e.varsValues.get(0));
            Person p2 = cList.elementOnIndex(e.varsValues.get(1));

            p1.removeRelationship(e.relations, p2);

            messageSb.append(p1.getNameOfPerson() + " " + e.relations + " " + p2.getNameOfPerson());

        }else{
            Person p1 = cList.elementOnIndex(e.varsValues.get(0));

            p1.removeFact(e.relations); //if false fakt neexistoval..

            messageSb.append(p1.getNameOfPerson() + " " + e.relations);
        }

        return messageSb.toString();
    }

    private String addByAction(Condition.Entry e, Combinations cList) {

        StringBuilder messageSb = new StringBuilder("pridaj : ");

        if (e.varsValues.size() > 1) {
            Person p1 = cList.elementOnIndex(e.varsValues.get(0));
            Person p2 = cList.elementOnIndex(e.varsValues.get(1));
            
            p1.addRelationship(e.relations, p2);

            messageSb.append(p1.getNameOfPerson() + " " + e.relations + " " + p2.getNameOfPerson());
        }else{
            Person p1 = cList.elementOnIndex(e.varsValues.get(0));
            
            p1.addFact(e.relations);

            messageSb.append(p1.getNameOfPerson() + " " + e.relations);
        }

        return messageSb.toString();
    }

    /*parsovanie akcii do pola*/
    private Condition parseAction(String action) {
        String[] infoFromAction = action.split(",");
        return new Condition(infoFromAction, condition);
    }

    /*parsovanie podmienok do pola*/
    private Condition parseCondition(String condition) {
        String[] infoFromCondition = condition.split(",");
        return new Condition(infoFromCondition, null);
    }

    public String toString() {

        StringBuilder rulesSB = new StringBuilder("MENO : " + this.nameOfRule + "\nAK : "+ this.condActOfRule);

        rulesSB.append("\n");
        return rulesSB.toString();

    }

    private void setCondActOfRule(String condition, String action) {
        this.condActOfRule = condition + "\nPOTOM: " + action;
    }
}
