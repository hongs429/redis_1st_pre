package redis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderServiceJava {

    // 상품 DB
    private final Map<String, Integer> productDatabase = new ConcurrentHashMap<>();
    // 가장 최근 주문 정보를 저장하는 DB
    private static final ThreadLocal<Map<String, OrderInformation>> latestOrderDatabase = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public OrderServiceJava() {
        // 초기 상품 데이터
        productDatabase.put("apple", 100);
        productDatabase.put("banana", 50);
        productDatabase.put("orange", 75);
    }

    // 주문 처리 메서드
    public void order(String productName, int amount) {

        productDatabase.compute(productName, (key, value) -> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }

            if (value != null && value >= amount) {
                System.out.println("Current Thread : " + Thread.currentThread().getName() +
                        " - CurrentStock : " + value + " - Order : " + amount + " - ProductName : " + productName);
                latestOrderDatabase.get()
                        .put(productName, new OrderInformation(productName, amount, System.currentTimeMillis()));
                return value - amount;
            }
            return value;
        });

    }

    // 재고 조회
    public int getStock(String productName) {
        return productDatabase.getOrDefault(productName, 0);
    }

    public void getLatestOrder() {
        Map<String, OrderInformation> orderInfoMap = latestOrderDatabase.get();
        List<OrderInformation> list = orderInfoMap.values().stream().toList();
        OrderInformation orderInformation = list.getLast();
        System.out.println(list);
        System.out.println("latest orderInfo = " + orderInformation);
    }
}