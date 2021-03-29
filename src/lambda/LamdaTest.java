package lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LamdaTest {
    public static void main(String[] args) {

        Runnable runnable = () -> System.out.println(5);
        runnable.run();


        Supplier<String> stringSupplier = () -> "a";
        String s = stringSupplier.get();
        System.out.println("s = " + s);


        Consumer<String> consumer = s1 -> System.out.println("s1 = " + s1);
        consumer.accept("consume");


        Function<String, Integer> function = s1 -> Integer.valueOf(s1);
        Integer apply = function.apply("101");
        System.out.println("apply = " + apply);


        Predicate<Integer> predicate = s1 -> s1 > 0;
        boolean test = predicate.test(-4);
        System.out.println("test = " + test);


    }
}
