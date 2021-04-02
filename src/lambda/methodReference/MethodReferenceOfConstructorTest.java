package lambda.methodReference;

import java.util.function.*;

public class MethodReferenceOfConstructorTest {
    public static void main(String[] args) {
        Supplier<ConstructorTest> s = () -> new ConstructorTest();
        Supplier<ConstructorTest> s2 = ConstructorTest::new;
        Function<String, ConstructorTest> f = (ss) -> new ConstructorTest(ss);
        Function<String, ConstructorTest> f2 = ConstructorTest::new;
        BiFunction<String, String, ConstructorTest> bf = (ss1, ss2) -> new ConstructorTest(ss1, ss2);
        BiFunction<String, String, ConstructorTest> bf2 = ConstructorTest::new;

        Function<Integer, int[]> ar = x -> new int[x];
        Function<Integer, int[]> ar2 = int[]::new;
        int[] intArr = ar2.apply(5);

        ConstructorTest ct = s2.get();
        ConstructorTest ct2 = f2.apply("s");
        ConstructorTest ct3 = bf2.apply("s", "s2");

        System.out.println("ct = " + ct);
        System.out.println("ct2 = " + ct2);
        System.out.println("ct3 = " + ct3);
        System.out.println("intArr size = " + intArr.length);

    }
}

class ConstructorTest {
    String s;
    String s2;

    public ConstructorTest() {
    }

    public ConstructorTest(String s) {
        this.s = s;
    }

    public ConstructorTest(String s, String s2) {
        this.s = s;
        this.s2 = s2;
    }

    @Override
    public String toString() {
        return "ConstructorTest{" +
                "s='" + s + '\'' +
                ", s2='" + s2 + '\'' +
                '}';
    }
}
