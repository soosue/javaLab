package generic.test1;

import java.util.ArrayList;

public class VegetableBox <T extends Vegetable> {
    private ArrayList<T> list = new ArrayList<>();

    public void add(T item) {
        list.add(item);
    }

    public T get(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
