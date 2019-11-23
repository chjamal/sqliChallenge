package test.entities;

import java.util.*;

public class Store {
    private String name;
    private List<String> documents;

    public Store() {
        this.documents = new ArrayList<>();
    }

    public Store(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getDocuments() {
        return documents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;
        Store store = (Store) o;
        return getName().equals(store.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public String displayContents() {
        StringBuilder s = new StringBuilder(name + ":");

        if (documents.isEmpty())
            return s.append("empty").toString();
        else
            return s.append(String.join(", ", documents)).toString();
    }

    public void clearContent(){
        documents.clear();
    }

    public void addDocs(String... doc){
        Collections.addAll(documents,doc);
        documents.addAll(Arrays.asList(doc));
    }
}
