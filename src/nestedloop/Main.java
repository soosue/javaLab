package nestedloop;

public class Main {
    public static void main(String[] args) {

        nested:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 3)
                    continue nested; //진행하던 for문(nested)의 증가부분(i++)로 넘어간다.

                System.out.println("i:" + i + ", j:"+ j);
            }
            System.out.println("for2 fin");
        }
        System.out.println("for1 fin");

        nested:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 3)
                    break nested; //진행하던 for문(nested)를 끝낸다.

                System.out.println("i:" + i + ", j:"+ j);
            }
            System.out.println("for2 fin");
        }
        System.out.println("for1 fin");
    }
}
