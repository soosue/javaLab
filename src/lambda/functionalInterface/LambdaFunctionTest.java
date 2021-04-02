package lambda.functionalInterface;

import java.util.function.*;

public class LambdaFunctionTest {
    public static void main(String[] args) {

        FunctionalInterfaceTest<String> functionalInterfaceTest = (s1) -> { System.out.println("s1 = " + s1); };
        functionalInterfaceTest.consume("@FunctionalInterface is not necessary, but ");

        Runnable runnables = ()-> System.out.println("1");
        runnables.run();

        Runnable runnable = () -> System.out.println("Runnable " + 5);
        runnable.run();


        Supplier<String> stringSupplier = () -> "a";
        String s = stringSupplier.get();
        System.out.println("Supplier s = " + s);

        Supplier<String>[] stringSupplier2 = new Supplier[]{() -> "a", () -> "b"};
        for (Supplier<String> supplier : stringSupplier2) {
            System.out.println("supplier.get() = " + supplier.get());
        }


        Consumer<String> consumer = s1 -> System.out.println("Consumer s1 = " + s1);
        consumer.accept("consume");


        Function<String, Integer> function = s1 -> Integer.valueOf(s1);
        Integer apply = function.apply("101");
        System.out.println("Function apply = " + apply);


        Predicate<Integer> predicate = s1 -> s1 > 0;
        boolean test = predicate.test(-4);
        System.out.println("Predicate test = " + test);


        BiConsumer<String, Integer> biConsumer = (s1, i1) -> System.out.println("BiConsumer s1 = " + s1 + ", i1 = " + i1);
        biConsumer.accept("3", 1);


        BiFunction<String, Integer, Integer> biFunction = (s1, i1) -> Integer.valueOf(s1) + i1;
        Integer apply1 = biFunction.apply("10", 1);
        System.out.println("BiFunction apply1 = " + apply1);


        BiPredicate<Integer, Integer> biPredicate = (i1, i2) -> i1 < i2;
        boolean test1 = biPredicate.test(1, 2);
        System.out.println("BiPredicate test1 = " + test1);


        UnaryOperator<String> unaryOperator = (s1) -> s1 + " works!";
        String unaryOperator1 = unaryOperator.apply("UnaryOperator");
        System.out.println("unaryOperator1 = " + unaryOperator1);


        BinaryOperator<String> binaryOperator = (s1, s2) -> s1 + " " + s2;
        String binaryOperator1 = binaryOperator.apply("BinaryOperator", "works!");
        System.out.println("binaryOperator1 = " + binaryOperator1);
    }
}
