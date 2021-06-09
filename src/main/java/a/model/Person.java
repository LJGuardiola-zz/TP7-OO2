package a.model;

import java.util.Set;

public class Person {

    private final int id;
    private final String name;
    private final Set<Phone> phones;

    public Person(int id, String name, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public Phone[] getPhones() {
        return phones.toArray(new Phone[0]);
    }

}
