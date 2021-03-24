package generic.test1;

public class Juicer {
    public static Juice makeJuice(FruitBox<?> box) {//? extends Fruit을 하지 않아도 밑에 Fruit을 받을 수 있게 되어있다.
        // 그 이유는 FruitBox<T extends Fruit>으로부터 FruitBox의 요소들이 Fruit의 자손들임을 이미 알고 있기 때문이다.
        //FruitBox<T extends Fruit>가 없었다면 Fruit으로 받을 수가 없다.
        String tmp = "";
        for (Fruit f :box.getList())// <- Fruit으로 받음.
            tmp += f + " ";
        return new Juice(tmp);
    }
}
