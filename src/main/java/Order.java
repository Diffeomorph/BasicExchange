import java.time.Instant;

public class Order {
    String buy_or_sell;
    double quantity;
    double price;
    Instant entry_time;
    int trade_id;

    Order(String b_or_s, double qty, double px){
        entry_time = Instant.now();
        quantity = qty;
        price = px;
        buy_or_sell = b_or_s;
        trade_id = OrderBook.min_trade_id + 1;
    }

}
