package a;

import a.model.Person;
import a.model.PersonDao;
import a.model.Phone;
import a.persistence.PersonDaoJdbc;

public class Main {
    public static void main(String[] args) {
        PersonDao dao = new PersonDaoJdbc();
        Person person = dao.getPersonById(1);
        System.out.println(person.getName());
        for (Phone phone : person.getPhones()) {
            System.out.println(phone.getNumber());
        }
    }
}
