package sashaVosu.firstWebApplication.service;

import org.junit.Test;


public class TestTest {

    @Test
    public void test1() {
        int threads = 500000;
        while (threads > 0) {
            threads -= 1;
            new Thread(() -> Singleton.getInstance().seyHey())
                    .start();
        }
        while (Singleton.getInstance().countHey < 500000) {
            System.out.println(Singleton.getInstance().countHey);
        }
        System.out.println("DONE");

    }
}

class Singleton {
    private static volatile Singleton INSTANCE;

    public volatile Integer countHey = 0;

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }

    public void seyHey() {
        synchronized (this) {
            countHey += 1;
        }
    }
}
