package lambda.methodReference;

import java.util.function.*;

public class MethodReferenceOfConstructorTest {
    public static void main(String[] args) {
        Supplier<ConstructorTest> s = ConstructorTest::new;
        Function<String, ConstructorTest> f = ConstructorTest::new;
        BiFunction<String, String, ConstructorTest> bf = ConstructorTest::new;

        ConstructorTest ct = s.get();
        ConstructorTest ct2 = f.apply("s");
        ConstructorTest ct3 = bf.apply("s", "s2");

        System.out.println("ct = " + ct);
        System.out.println("ct2 = " + ct2);
        System.out.println("ct3 = " + ct3);

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
