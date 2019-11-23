package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CloudInfrastructure {
    private List<Store> stores;
    private List<Machine> machines;

    public CloudInfrastructure() {
        this.stores = new ArrayList<>();
    }

    public void createStore(String storeName) {
        Store newStore = new Store(storeName);
        /*if (stores.contains(newStore)) throw new CreateStoreException("error");
        else*/
        stores.add(newStore);
    }

    public void uploadDocument(String storeName, String... content) {
        Store store1 = new Store(storeName);
        stores.stream()
                .filter(store -> store.equals(store1))
                .forEach(store -> store.addDocs(content));
    }

    public String listStores() {
        return stores.stream()
                .map(Store::displayContents)
                .collect(Collectors.joining("||"));
    }

    public void deleteStore(String storeName) {
        Store oldStore = new Store(storeName);
        stores.remove(oldStore);
    }

    public void emptyStore(String storeName) {
        Store store1 = new Store(storeName);
        stores.stream()
                .filter(store -> store.equals(store1))
                .findAny()
                .ifPresent(Store::clearContent);
    }


    public void createMachine(String name, String os, String diskSize, String memory) {
    }

    public String listMachines() {
        return null;
    }

    public void startMachine(String machineName) {
    }

    public void stopMachine(String machineName) {
    }
}
