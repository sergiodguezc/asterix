package utils;

public class Entero {

    private int value;

    public Entero(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }

    public void add(int delta) {
        this.value += delta;
    }

    public String toString() {
        return Integer.toString(value);

    }

}
