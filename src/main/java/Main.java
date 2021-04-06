import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Program...");
        PriceProcess priceProcess = new PriceProcess();
        OrderBook book = new OrderBook();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(priceProcess.queryPrice());

        book.sendBuyLimitOrder(1000,100);
        book.sendBuyLimitOrder(1000,102);
        book.sendBuyLimitOrder(1000,104);
        book.sendBuyLimitOrder(2050,106);

        book.sendSellLimitOrder(1000,103);
        book.sendSellLimitOrder(1000,108);
        book.sendSellLimitOrder(1000,110);

        book.printOrderBook();
        System.out.println(book.findMaxBuy());
        System.out.println(book.findMinSell());
    }
}
