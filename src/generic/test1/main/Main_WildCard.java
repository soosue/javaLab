package generic.test1.main;

import generic.test1.*;

public class Main_WildCard {
    public static void main(String[] args) {
        FruitBox<Fruit> fruitBox = new FruitBox<>();
        FruitBox<Apple> appleBox = new FruitBox<>();

        fruitBox.add(new Apple());
        fruitBox.add(new Cherry());

        appleBox.add(new Apple());
        appleBox.add(new Apple());

        System.out.println("Juicer.makeJuice(appleBox) = " + Juicer.makeJuice(appleBox));
        System.out.println("Juicer.makeJuice(fruitBox) = " + Juicer.makeJuice(fruitBox));

    }
}
