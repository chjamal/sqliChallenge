package test;

import test.entities.Machine;
import test.entities.Status;
import test.entities.Store;
import test.exception.CreateStoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CloudInfrastructure {
    private List<Store> stores;
    private List<Machine> machines;

    public CloudInfrastructure() {
        this.stores = new ArrayList<>();
        this.machines = new ArrayList<>();
    }

    public void createStore(String storeName) {
        Store newStore = new Store(storeName);
        if (!stores.contains(newStore)) stores.add(newStore);
        else throw new CreateStoreException();
    }

    public void uploadDocument(String storeName, String... content) {
        Store store1 = new Store(storeName);
        stores.stream()
                .filter(store -> store.equals(store1))
                .findFirst()
                .ifPresent(store -> {
                    store.addDocs(content);
                });
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
        this.findStore(storeName)
                .ifPresent(Store::clearContent);
    }

    public void createMachine(String name, String os, String diskSize, String memory) {
        Machine newMachine = new Machine(name, os, diskSize, memory);
        if (!machines.contains(newMachine)) machines.add(newMachine);
    }

    public String listMachines() {
        return machines.stream()
                .map(Machine::displayContents)
                .collect(Collectors.joining("||"));
    }

    public void startMachine(String machineName) {
        this.findMachine(machineName)
                .ifPresent(Machine::start);
    }

    public void stopMachine(String machineName) {
        this.findMachine(machineName)
                .ifPresent(Machine::stop);
    }

    public double usedDisk(String machineName) {
        Optional<Machine> machine = this.findMachine(machineName);
        Optional<Store> store = this.findStore(machineName);

        if (machine.isPresent()) {
            return machine.get().getDiskSize();
        } else if (store.isPresent()) {
            return store.get()
                    .getDocuments()
                    .size() * 0.100;
        }

        return 0;
    }

    public double usedMemory(String machineName) {
        Optional<Machine> optionalMachine = this.findMachine(machineName);

        if (optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            if (machine.isRunning())
                return optionalMachine.get().getMemory();
            else return 0;
        }
        return 0;
    }

    private Optional<Machine> findMachine(String machineName) {
        return machines.stream()
                .filter(machine -> machine.getName().equals(machineName))
                .findFirst();
    }

    private Optional<Store> findStore(String storeName) {
        return stores.stream()
                .filter(store -> store.equals(new Store(storeName)))
                .findFirst();
    }

    public double globalUsedDisk() {
        Double globalUse = 0.;

        globalUse += machines.stream()
                .map(machine -> this.usedDisk(machine.getName()))
                .reduce(Double::sum)
                .orElse(0.);

        globalUse += stores.stream()
                .map(store -> this.usedDisk(store.getName()))
                .reduce(Double::sum)
                .orElse(0.);

        return globalUse;
    }

    public double globalUsedMemory() {
        return machines.stream()
                .map(machine -> this.usedMemory(machine.getName()))
                .reduce(Double::sum)
                .orElse(0.);
    }
}
