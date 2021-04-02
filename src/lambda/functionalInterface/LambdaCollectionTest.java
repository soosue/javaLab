package lambda.functionalInterface;

import java.util.ArrayList;
import java.util.List;

public class LambdaCollectionTest {
    public static void main(String[] args) {


        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }


        //list에서 홀수만 출력하시오.
        list.forEach(j -> {
            if ( j % 2 != 0 ) {
                System.out.println("print odd number = " + j);
            }
        });
        System.out.println("list = " + list);

        //list에서 짝수를 제거하시오.
        list.removeIf(j -> j % 2 == 0);
        System.out.println("remove even number = " + list);


        //list의 값들을 모두 2배하시오.
        list.replaceAll(j -> j * 2);
        System.out.println("multiply by 2 = " + list);

    }
}
