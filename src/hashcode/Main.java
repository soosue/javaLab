package hashcode;

import java.util.HashMap;

public class Main {
    /*
        해쉬코드에 대해 궁금한 점을 테스트 해본다.

        1. 객체 toString하면 Object@b3ca87d 하고 나오는 @뒤의 값은 hashcode 인가?
        2. hashcode가 같을 때, hashmap에서 값을 찾아올 수 있는지 테스트.
        3. hashmap에 put을 하게 되면 Node<K, V>[]의 size는 어떻게 되는지?

     */
    /*
        1. 같다
        2. 찾아올 수 있다.
        3. resize() 를 통해 늘어난다.

        hashmap의 동작원리
        -> hashcode를 이용하여 값을 찾을 수 있게 되어있다. (배열을 이용함. O(1))
        -> Node<K, V>[]을 이용하여 값들을 저장하고 있음.
        -> put : 해당 hashcode값으로 Node<K, V>[]이 자리를 차지 안하고 있으면 newNode(hash, key, value, null(next));
                 차지하고 있으면, p.next = newNode(hash, key, value, null(next));
        -> get : Node<K, V>[] 배열에서 hashcode값으로 찾고 Node의 key가 같으면 반환.
                 만약 put 할 때, hashcode가 중복돼서 해당 hashcode값의 Node가 여러 개라면 순회를 통해서 맞는 key값을 찾아서 반환.

     */
    public static void main(String[] args) {
        // 1.
        Object object = new Object();
        System.out.println(object.toString());
        System.out.println(Integer.toHexString(object.hashCode()));

        // 2.
        SameHashCodeObject kim = new SameHashCodeObject(1l, "kim");
        SameHashCodeObject lee = new SameHashCodeObject(2l, "lee");
        SameHashCodeObject park = new SameHashCodeObject(3l, "park");

        HashMap<SameHashCodeObject, SameHashCodeObject> hashMap = new HashMap<>();
        hashMap.put(kim, kim);
        hashMap.put(lee, lee);
        hashMap.put(park, park);
        System.out.println(hashMap.get(kim));
        System.out.println(hashMap.get(lee));
        System.out.println(hashMap.get(park));


        // 3.
        HashMap<String, Integer> iMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            iMap.put("" + i, i);
        }

        System.out.println(iMap.get("1"));
    }

    static class SameHashCodeObject {
        private Long id;
        private String name;

        public SameHashCodeObject(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
