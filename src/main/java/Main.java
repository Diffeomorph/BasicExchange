import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Program...");

        OrderBook book = new OrderBook();

        int limitMinimum = 50;
        int limitMaximum = 150;

        Random rand = null;
        int randNum = rand.nextInt(limitMaximum-limitMinimum) + limitMinimum;

        int quantityMinimum = 50;
        int quantityMaximum = 150;

        Random rand2 = null;
        int randNum2 = rand2.nextInt(quantityMaximum-quantityMinimum) + quantityMinimum;

        

        book.sendBuyLimitOrder(1000,100);
        book.sendBuyLimitOrder(1000,102);
        book.sendBuyLimitOrder(1000,104);
        book.sendBuyLimitOrder(2050,106);

        book.sendSellLimitOrder(1000,103);
        book.sendSellLimitOrder(1000,108);
        book.sendSellLimitOrder(1000,110);

        book.printOrderBook();

    }

    public static void main2(String[] args) throws InterruptedException {
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
