import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Program...");
        PriceProcess p = new PriceProcess();
        OrderBook book = new OrderBook();
        TimeUnit.SECONDS.sleep(30);
        System.out.println(p.query_price());
        TimeUnit.SECONDS.sleep(30);
        System.out.println(p.query_price());
    }
}
