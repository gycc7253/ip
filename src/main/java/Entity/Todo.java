package main.java.Entity;

public class Todo extends Task {

    public Todo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T][" + (this.isDone ? "X" : " ") + "] " + this.name;
    }
}
