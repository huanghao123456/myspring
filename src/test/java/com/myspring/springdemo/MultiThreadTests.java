package com.myspring.springdemo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * 多线程本地单元测试类
 * <p>Java8引入了CompletableFuture，这也是推荐的多线程开启方式，应当抛弃对FutureTask的使用
 */
public class MultiThreadTests {

    /**
     * 有如下动作：
     * <li>1. 小白先入座位</li>
     * <li>2. 小白点开始点菜</li>
     * <li>3. 小白打王者的同时厨师做饭</li>
     * <li>4. 厨师做完菜后小白开始吃饭</li>
     */
    @Test
    public void myMultiThreadDemo1() {
        System.out.println("小白入座");
        System.out.println("小白点菜");

        CompletableFuture<String> cookThread = CompletableFuture.supplyAsync(() -> {
            safeSleep(200);
            System.out.println("厨师切菜");
            safeSleep(100);
            System.out.println("厨师上菜");
            return "西红柿炒鸡蛋";
        });

        System.out.println("小白打王者");
        String food = cookThread.join();
        System.out.println("小白恰饭: " + food);
    }

    /**
     * 若厨师做完菜后把上菜的任务交给服务员，即有如下流程：
     * <li>1. 小白先入座位</li>
     * <li>2. 小白点开始点菜</li>
     * <li>3. 小白打王者的同时厨师做饭</li>
     * <li>4. 厨师做完菜后服务员上菜</li>
     * <li>5. 小白吃服务员上来的菜</li>
     */
    @Test
    public void myMultiThreadDemo2() {
        System.out.println("小白入座");
        System.out.println("小白点菜");

        CompletableFuture<String> cookThread = CompletableFuture.supplyAsync(() -> {
            safeSleep(200);
            System.out.println("厨师切菜");
            safeSleep(100);
            System.out.println("厨师做菜");
            return "西红柿炒鸡蛋";
        });

        CompletableFuture<String> waiterThread = CompletableFuture.supplyAsync(() -> {
            safeSleep(150);
            System.out.println("服务员上菜");
            return cookThread.join();
        });

        System.out.println("小白打王者");
        String food = waiterThread.join();
        System.out.println("小白恰饭: " + food);
    }

    /**
     * 进一步的，CompletableFuture中提供了更优雅的方式，thenCompose（强调线程前后顺序）方法可以代替join这种生硬的等待方式，更直观的表示线程前后顺序
     */
    @Test
    public void myMultiThreadDemo3() {
        System.out.println("小白入座");
        System.out.println("小白点菜");

        CompletableFuture<String> cookThread = CompletableFuture.supplyAsync(() -> {
            safeSleep(200);
            System.out.println("厨师切菜");
            safeSleep(100);
            System.out.println("厨师做菜");
            return "西红柿炒鸡蛋";
        }).thenCompose(dish -> CompletableFuture.supplyAsync(() -> {
            safeSleep(150);
            System.out.println("服务员上菜");
            return dish;
        }));

        System.out.println("小白打王者");
        String food = cookThread.join();
        System.out.println("小白恰饭: " + food);
    }

    /**
     * 进一步的，如果厨师做西红柿炒鸡蛋，服务员同时帮忙热米饭，两步骤都完成后才会上给小白，即如下步骤：
     * <li>1. 小白先入座位</li>
     * <li>2. 小白点开始点菜</li>
     * <li>3. 小白打王者的同时厨师做饭</li>
     * <li>4. 厨师做菜的同时服务员热米饭</li>
     * <li>5. 米饭和菜都好后小白开始恰饭</li>
     */
    @Test
    public void myMultiThreadDemo4() {
        System.out.println("小白入座");
        System.out.println("小白点菜");
        System.out.println("小白打王者");

        CompletableFuture<String> cookThread = CompletableFuture.supplyAsync(() -> {
            safeSleep(200);
            System.out.println("厨师切菜");
            safeSleep(100);
            System.out.println("厨师做菜");
            return "西红柿炒鸡蛋";
        });

        CompletableFuture<String> waiterThread = CompletableFuture.supplyAsync(() -> {
            safeSleep(200);
            System.out.println("服务员热米饭");
            return "热米饭";
        });

        String food = cookThread.join() + waiterThread.join();
        System.out.println("打包");
        System.out.println("小白恰饭: " + food);
    }

    /**
     * 两个join去等待，最后单独打包的方式并不好看，我们还有更优雅的写法thenCombine（强调获取两线程的执行结果）
     */
    @Test
    public void myMultiThreadDemo5() {
        System.out.println("小白入座");
        System.out.println("小白点菜");
        System.out.println("小白打王者");

        CompletableFuture<String> cookAndWaiterThread = CompletableFuture.supplyAsync(() -> {
            safeSleep(200);
            System.out.println("厨师切菜");
            safeSleep(100);
            System.out.println("厨师做菜");
            return "西红柿炒鸡蛋";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            safeSleep(200);
            System.out.println("服务员热米饭");
            return "热米饭";
        }), (dish, rice) -> {
            System.out.println("打包");
            return dish + rice;
        });

        System.out.println("小白恰饭: " + cookAndWaiterThread.join());
    }

    /**
     * 考虑有如下情形：
     * 小白等车，有700路公交和800路公交，两公交谁先来上谁
     */
    @Test
    public void myMultiThreadDemo6() {
        System.out.println("小白等车");

        CompletableFuture<String> bus1Thread = CompletableFuture.supplyAsync(() -> {
            safeSleep(700);
            System.out.println("700路公交到站");
            return "700路公交到站";
        });

        CompletableFuture<String> bus2Thread = CompletableFuture.supplyAsync(() -> {
            safeSleep(800);
            System.out.println("800路公交到站");
            return "800路公交到站";
        });

        long currentTimeMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTimeMillis <= 1000) {
            if (bus1Thread.isDone() || bus2Thread.isDone()) {
                if (bus1Thread.isDone()) {
                    System.out.println("小白坐700路");
                    break;
                } else if (bus2Thread.isDone()) {
                    System.out.println("小白坐800路");
                    break;
                }
            }
        }
    }

    /**
     * 上面的代码捞的不行，while和if这种自旋等待不够优雅，这种“将就”是公司里代码越堆越烂的本质所在，多接触一些API是好的
     * <p>比如applyToEither方法
     */
    @Test
    public void myMultiThreadDemo7() {
        System.out.println("小白等车");

        CompletableFuture<String> busThread = CompletableFuture.supplyAsync(() -> {
            safeSleep(700);
            System.out.println("700路公交到站");
            return "700路";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            safeSleep(800);
            System.out.println("800路公交到站");
            return "800路";
        }), firstBus -> firstBus);

        System.out.println("小白坐" + busThread.join());
    }

    /**
     * 链式调用中更简单的处理异常的方式：exceptionally
     * <p>在之前的基础上，假设有这样场景，700路公交撞树上了，抛出了异常，小白对这种异常的处理方式是换乘出租车
     * <p>这时使用exceptionally而非冗长的try-catch
     */
    @Test
    public void myMultiThreadDemo8() {
        System.out.println("小白等车");

        CompletableFuture<String> busThread = CompletableFuture.supplyAsync(() -> {
            safeSleep(700);
            System.out.println("700路公交到站");
            return "700路";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            safeSleep(800);
            System.out.println("800路公交到站");
            return "800路";
        }), firstBus -> {
            if (firstBus.startsWith("700路")) {
                throw new RuntimeException("700路撞树了");
            }
            return firstBus;
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            System.out.println("换乘出租车");
            return "出租车到了";
        });

        System.out.println("小白坐" + busThread.join());
    }

    @Test
    public void myMultiThreadDemo9() {
        // 查看cpu核心数
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(ForkJoinPool.commonPool().getPoolSize());
        System.out.println(ForkJoinPool.getCommonPoolParallelism());
        long currentTimeMillis = System.currentTimeMillis();
        CompletableFuture<?>[] dishes = IntStream.rangeClosed(1, 12)
                .mapToObj(i -> new Dish("菜" + i))
                .map(dish -> CompletableFuture.runAsync(dish::make))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(dishes).join();
        long l = System.currentTimeMillis() - currentTimeMillis;
        System.out.println(l);
        System.out.println("主线程结束");
    }

    private static class Dish {
        private String name;

        public Dish(String name) {
            this.name = name;
        }

        public String make() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "已经完成");
            return name + "已经完成";
        }
    }

    private void safeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
