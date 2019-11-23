package test.entities;

import test.exception.MachineStateException;

import java.util.Objects;


public class Machine {
    private String name;
    private String os;
    private double diskSize;
    private double usedDiskSize;
    private double memory;
    private Status status;

    public Machine() {
        this.status = Status.inactive;
    }

    public Machine(String name, String os, String diskSize, String memory) {
        this();
        this.name = name;
        this.os = os;
        this.diskSize = Double.parseDouble(diskSize.split("gb")[0]);
        this.memory = Double.parseDouble(memory.split("gb")[0]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public double getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(double diskSize) {
        this.diskSize = diskSize;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getUsedDiskSize() {
        return usedDiskSize;
    }

    public void setUsedDiskSize(double usedDiskSize) {
        this.usedDiskSize = usedDiskSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Machine)) return false;
        Machine machine = (Machine) o;
        return getDiskSize() == machine.getDiskSize() &&
                getMemory() == machine.getMemory() &&
                Objects.equals(getName(), machine.getName()) &&
                Objects.equals(getOs(), machine.getOs()) &&
                getStatus() == machine.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getOs(), getDiskSize(), getMemory(), getStatus());
    }

    public boolean isRunning(){
        return this.getStatus().equals(Status.running);
    }

    public String displayContents() {
        return name + ":" + status.name();
    }

    public void start() {
        if (status.equals(Status.running))
            throw new MachineStateException();
        else this.setStatus(Status.running);
    }

    public void stop() {
        if (status.equals(Status.stopped))
            throw new MachineStateException();
        else this.setStatus(Status.stopped);
    }
}
