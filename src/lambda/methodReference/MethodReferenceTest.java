package lambda.methodReference;

import java.util.function.*;

public class MethodReferenceTest {
    public static void main(String[] args) {
        /* 3가지 케이스가 있다.
         *
         * 인스턴스 메소드,
         * 특정 객체의 인스턴스 메소드,
         * static 메소드,
         *
         */
        RefTest ref = new RefTest();

        BiConsumer<RefTest, String> c = RefTest::test;
        Consumer<String> c2 = ref::test;
        Consumer<String> c3 = RefTest::staticTest;

        c.accept(new RefTest(), "p");
        c2.accept("p2");
        c3.accept("p3");



        Function<String, Integer> f = Integer::parseInt;// s1 -> Integer.parseInt(s1); //static 메소드
        System.out.println("f.apply(\"3\") = " + f.apply("3"));

        Predicate<String> p = ""::equals;// s1 -> "".equals(s1); // 특정객체의 인스턴스 메소드
        System.out.println("p.test(\"\") = " + p.test(""));

        BiPredicate<String, String> bp = String::equals;// (s1, s2) -> s1.equals(s2); //인스턴스 메소드
        System.out.println("bp.test(\"a\", \"b\") = " + bp.test("a", "b"));
        System.out.println("bp.test(\"c\", \"c\") = " + bp.test("c", "c"));

        String sTmp = "hi";
        Predicate<String> p2 = sTmp::equals;
        System.out.println("p2.test(\"hi\") = " + p2.test("hi"));
        System.out.println("p2.test(\"gi\") = " + p2.test("gi"));


    }
}

class RefTest {
    public void test(String s) {

    }

    public static void staticTest(String s) {

    }
}