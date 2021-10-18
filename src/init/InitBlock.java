package init;

import java.util.HashMap;

public class InitBlock {
    public HashMap<String, String> map = new HashMap<>();

    public static HashMap<String, String> map2 = new HashMap<>();

    static {
        System.out.println("static init block 1번만 호출");
        map2.put("22", "aa");
    }
    {
        System.out.println("init block 생성자 전 호출");
        map.put("hi", "good");
    }

    public InitBlock() {
        System.out.println("생성자 호출");
        map.put("hi2", "bad");
    }
}
