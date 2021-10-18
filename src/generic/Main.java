package generic;

public class Main {
    public static void main(String[] args) {
        Test<String> stringTest = new Test<>(1);
        System.out.println(stringTest.element);
    }
}
