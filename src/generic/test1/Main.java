package generic.test1;

public class Main {
    public static void main(String[] args) {
        FruitBox<Fruit> fruitBox = new FruitBox<>();//(1)
        FruitBox<Apple> appleBox = new FruitBox<>();//(2)
        FruitBox<Cherry> cherryBox = new FruitBox<>();
        //FruitBox<Tomato> tomatoBox = new FruitBox<>(); //error. Type parameter 'test1.Tomato' is not within its bound; should extend 'test1.Fruit'

        fruitBox.add(new Apple());//(3)
        fruitBox.add(new Cherry());//(4)

        //(1),(2)의 관계 T extends Fruit (한정된 제네릭)과
        //(3),(4)의 관계 Apple extends Fruit (상속)은 엄연히 다르다.

        //interface로도 제네릭을 한정시킬 수 있다.
        //T extends Eatable
        //T extends Fruit & Eatable : 타입변수가 Fruit도 상속하면서 Eatable도 implements해야함.

        VegetableBox<Tomato> tomatoBox = new VegetableBox<>();
        //VegetableBox<Apple> appleBox2 = new VegetableBox<Apple>(); //error. Type parameter 'test1.Apple' is not within its bound; should extend 'test1.Vegetable'

        tomatoBox.add(new Tomato());
        //fruitBox.add(new Tomato());//error. Required type:Fruit Provided:Tomato
    }
}
