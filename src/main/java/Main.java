import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Program...");
        PriceProcess priceProcess = new PriceProcess();
        OrderBook book = new OrderBook();
        TimeUnit.SECONDS.sleep(10);
        System.out.println(priceProcess.queryPrice());

        book.sendBuyLimitOrder(1000,100);
        book.sendSellLimitOrder(1000,100);
        book.printOrderBook();
        System.out.println(book.findMaxBuy());
        System.out.println(book.findMinSell());
    }
}
