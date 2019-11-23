package test;

enum Status {
    Inactive, Running, Stopped;
}

public class Machine {
    private String name;
    private String os;
    private long diskSize;
    private long memory;
    private Status status;

    public Machine() {
        this.status = Status.Inactive;
    }

    public Machine(String name, String os, long diskSize, long memory) {
        this();
        this.name = name;
        this.os = os;
        this.diskSize = diskSize;
        this.memory = memory;
    }


}
