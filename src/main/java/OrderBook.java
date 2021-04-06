import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;


public class OrderBook {
    static int MIN_TRADE_ID = 0;
    OrderTree buys = new OrderTree();
    OrderTree sells = new OrderTree();

    OrderBook(){
        reset();
    }

    public void reset(){
        buys.reset();
        sells.reset();
        MIN_TRADE_ID = 0;
    }

    public void send_buy_order(double size){
        //create new order
        Order new_order = new Order("buy", size, Double.POSITIVE_INFINITY);
        while (size > 0 && buys.getDepth() > 0){
            LinkedList<Order> new_list = buys.maxPriceList();
            int i = 0;
            while (i < new_list.size() && size > 0) {
                Order current_order = new_list.get(i);
                double current_quantity = current_order.getQuantity();
                if (size > current_quantity){
                    size -= current_order.getQuantity();
                    // delete order
                    buys.delete_order(current_order.getTradeId());
                    Logger.Log(new_order.getTradeId(), "buy", current_order.getPrice(), size);
                    Logger.Log(current_order.getTradeId(), "sell", current_order.getPrice(), size);

                } else {
                    current_order.setQuantity(current_quantity - size);
                    Logger.Log(new_order.getTradeId(), "buy", current_order.getPrice(), size);
                    Logger.Log(current_order.getTradeId(), "sell", current_order.getPrice(), size);
                }
                i++;
            }
        }
        if (size > 0){
            new_order.setPrice(buys.maxPrice());
            buys.addOrder(new_order);
        }

    }


    public void send_sell_order(double size){
        Order new_order = new Order("sell", size, -Double.POSITIVE_INFINITY);

        while (size > 0 && sells.getDepth() > 0){
            LinkedList<Order> new_list = sells.min_price_list();
            int i = 0;
            while (i < new_list.size() && size > 0) {
                Order current_order = new_list.get(i);
                double current_quantity = current_order.getQuantity();
                if (size > current_quantity){
                    size -= current_order.getQuantity();
                    // delete order
                    sells.delete_order(current_order.getTradeId());
                    Logger.Log(new_order.getTradeId(), "sell", current_order.getPrice(), size);
                    Logger.Log(current_order.getTradeId(), "buy", current_order.getPrice(), size); //check log timing here
                } else {
                    current_order.setQuantity(current_quantity - size);
                    Logger.Log(new_order.getTradeId(), "buy", current_order.getPrice(), size);
                    Logger.Log(current_order.getTradeId(), "sell", current_order.getPrice(), size);
                }
                i++;
            }
        }
        if (size > 0){
            new_order.setPrice(sells.minPrice());
            sells.addOrder(new_order);
        }
    }

    public Double find_max_buy(){
        return buys.maxPrice();
    }

    public Double find_min_sell(){
        return sells.minPrice();
    }

}
