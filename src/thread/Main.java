package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // ThreadPoolExecutor는 poolSize까지 다 차면, 추가분에 대해서 기다린다.
        // shutdown 해주지 않으면, 생성된 스레드는 계속 살아있다.

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable r1 = () -> {
            int cnt = 0;
            while(true) {
                try {
                    System.out.println("a");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cnt++;
                if (cnt == 5)
                    break;
            }
        };
        Runnable r2 = () -> {
            int cnt = 0;
            while(true) {
                try {
                    System.out.println("b");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cnt++;
                if (cnt == 5)
                    break;
            }
        };
        Runnable r3 = () -> {
            int cnt = 0;
            while(true) {
                try {
                    System.out.println("c");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cnt++;
                if (cnt == 5)
                    break;
            }
        };
        Runnable r4 = () -> {
            int cnt = 0;
            while(true) {
                try {
                    System.out.println("d");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cnt++;
                if (cnt == 5)
                    break;
            }
        };
        Runnable r5 = () -> {
            int cnt = 0;
            while(true) {
                try {
                    System.out.println("e");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cnt++;
                if (cnt == 5)
                    break;
            }
        };
        List<Runnable> runnables = new ArrayList<>();
        runnables.add(r1);
        runnables.add(r2);
        runnables.add(r3);
        runnables.add(r4);
        for (Runnable runnable : runnables) {
            executorService.execute(runnable);
        }
        System.out.println("fin");

//        executorService.shutdown();
        executorService.execute(r5);
        System.out.println("shutdown fin");
    }
}
