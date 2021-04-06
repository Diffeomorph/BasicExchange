import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Program...");
        PriceProcess priceProcess = new PriceProcess();
        OrderBook book = new OrderBook();
        TimeUnit.SECONDS.sleep(30);
        System.out.println(priceProcess.queryPrice());
        //TimeUnit.SECONDS.sleep(30);
        //System.out.println(priceProcess.queryPrice());
        book.sendBuyOrder(1000);
    }
}
