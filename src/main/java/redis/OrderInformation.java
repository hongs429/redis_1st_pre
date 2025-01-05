package redis;

public class OrderInformation {
 private String productName;
 private int amount;
 private long currentTimeMillis;


    public OrderInformation(String productName, int amount, long currentTimeMillis) {
    this.productName = productName;
    this.amount = amount;
    this.currentTimeMillis = currentTimeMillis;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public long getCurrentTimeMillis() {
    return currentTimeMillis;
  }

  public void setCurrentTimeMillis(long currentTimeMillis) {
    this.currentTimeMillis = currentTimeMillis;
  }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "productName='" + productName + '\'' +
                ", amount=" + amount +
                ", currentTimeMillis=" + currentTimeMillis +
                '}';
    }
}
