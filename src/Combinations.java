import java.util.ArrayList;


public class Combinations {
    private ArrayList<Person> trioComb = new ArrayList<>();

    Combinations(Person p1, Person p2, Person p3) {
        trioComb.add(p1);
        trioComb.add(p2);
        trioComb.add(p3);
    }

    Combinations(Person p1, Person p2, Person p3, Person p4) {
        trioComb.add(p1);
        trioComb.add(p2);
        trioComb.add(p3);
        trioComb.add(p4);
    }

    ArrayList<Person> getTrioComb() {
        return trioComb;
    }

    Person elementOnIndex(int index) {
        return trioComb.get(index);
    }
}
