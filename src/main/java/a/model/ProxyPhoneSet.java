package a.model;

import java.util.*;

public class ProxyPhoneSet implements Set<Phone> {

    private final PersonDao dao;
    private boolean loaded;
    private final int personId;
    private final Set<Phone> phones;

    public ProxyPhoneSet(PersonDao dao, int personId) {
        this.dao = dao;
        this.loaded = false;
        this.personId = personId;
        this.phones = new HashSet<>();
    }

    private Set<Phone> getPhones() {
        if (!loaded) {
            phones.addAll(
                    dao.getPhonesByPersonId(personId)
            );
            loaded = true;
        }
        return phones;
    }

    @Override
    public int size() {
        return phones.size();
    }

    @Override
    public boolean isEmpty() {
        return phones.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getPhones().contains(o);
    }

    @Override
    public Iterator<Phone> iterator() {
        return getPhones().iterator();
    }

    @Override
    public Object[] toArray() {
        return getPhones().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return getPhones().toArray(a);
    }

    @Override
    public boolean add(Phone phone) {
        return getPhones().add(phone);
    }

    @Override
    public boolean remove(Object o) {
        return getPhones().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getPhones().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Phone> c) {
        return getPhones().addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return getPhones().retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return getPhones().removeAll(c);
    }

    @Override
    public void clear() {
        getPhones().clear();
    }

}
