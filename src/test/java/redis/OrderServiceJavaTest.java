package redis;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class OrderServiceJavaTest {
  private final Random random = new Random();
  private final OrderServiceJava service = new OrderServiceJava();

  @Test
  void testConcurrentOrders() throws InterruptedException {
    String productName = "apple";
    int initialStock = service.getStock(productName);

    int orderAmount = 8;
    int threadCount = 100;

    ExecutorService executor = newFixedThreadPool(threadCount);
    CountDownLatch latch = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
      executor.execute(() -> {
        try {
          service.order(productName, orderAmount);
        } finally {
          latch.countDown(); // 작업 완료 후 카운트 감소
        }
      });
    }

    latch.await();
    executor.shutdown();

    int expectedStock = initialStock % orderAmount;
    int actualStock = service.getStock(productName);

    System.out.println("Expected Stock: " + expectedStock + ", Actual Stock: " + actualStock);

    assertEquals(expectedStock, actualStock);
  }


  @Test
  void testLatestOrder() throws InterruptedException {
    String[] productNames = {"apple", "banana", "orange"};

    int threadCount = 10;

    ExecutorService executor = newFixedThreadPool(threadCount);
    CountDownLatch latch = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
      executor.execute(() -> {

        int orderCount = random.nextInt(1, 4);

        for (int j = 0; j < orderCount; j++) {
          service.order(randomProduct(productNames), randomAmount(1, 10));
        }
        service.getLatestOrder();

        latch.countDown();
      });
    }

    latch.await();
    executor.shutdown();
  }

  private int randomAmount(int min, int max) {
    return random.nextInt(min, max);
  }

  private String randomProduct(String[] productNames) {

    return productNames[random.nextInt(productNames.length)];
  }
}

