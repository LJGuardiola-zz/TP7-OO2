package a.model;

import java.util.Set;

public interface PersonDao {
    Person getPersonById(int id);
    Set<Phone> getPhonesByPersonId(int personId);
}
