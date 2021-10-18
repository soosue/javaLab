package generic;

public class Test<T> {
    private T[] data = (T[]) new Object[10];
    private Object[] data2 = {};

    public T[] element;

    public Test(int type) {
        if (type == 1) {
            element = data;
        }else if (type == 2) {
            element = (T[])data2;
        }
    }
}
