import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;


public class OrderBook {
    static int min_trade_id = 0;
    OrderTree buys = new OrderTree();
    OrderTree sells = new OrderTree();

    OrderBook(){
        reset();
    }

    public void reset(){
        buys.reset();
        sells.reset();
        min_trade_id = 0;
    }

    public void send_buy_order(double size){
        //create new order
        Order new_order = new Order("buy", size, Double.POSITIVE_INFINITY);
        while (size > 0 && buys.depth > 0){
            LinkedList<Order> new_list = buys.max_price_list();
            int i = 0;
            while (i < new_list.size() && size > 0) {
                Order current_order = new_list.get(i);
                double current_quantity = current_order.quantity;
                if (size > current_quantity){
                    size -= current_order.quantity;
                    // delete order
                    buys.delete_order(current_order.trade_id);
                    Logger.Log(new_order.trade_id, "buy", current_order.price, size);
                    Logger.Log(current_order.trade_id, "sell", current_order.price, size);

                } else {
                    current_order.quantity = current_quantity - size;
                    Logger.Log(new_order.trade_id, "buy", current_order.price, size);
                    Logger.Log(current_order.trade_id, "sell", current_order.price, size);
                }
                i++;
            }
        }
        if (size > 0){
            new_order.price = buys.max_price();
            buys.add_order(new_order);
        }

    }


    public void send_sell_order(double size){
        Order new_order = new Order("sell", size, -Double.POSITIVE_INFINITY);

        while (size > 0 && sells.depth > 0){
            LinkedList<Order> new_list = sells.min_price_list();
            int i = 0;
            while (i < new_list.size() && size > 0) {
                Order current_order = new_list.get(i);
                double current_quantity = current_order.quantity;
                if (size > current_quantity){
                    size -= current_order.quantity;
                    // delete order
                    sells.delete_order(current_order.trade_id);
                    Logger.Log(new_order.trade_id, "sell", current_order.price, size);
                    Logger.Log(current_order.trade_id, "buy", current_order.price, size);
                } else {
                    current_order.quantity = current_quantity - size;
                    Logger.Log(new_order.trade_id, "buy", current_order.price, size);
                    Logger.Log(current_order.trade_id, "sell", current_order.price, size);
                }
                i++;
            }
        }
        if (size > 0){
            new_order.price = sells.min_price();
            sells.add_order(new_order);
        }
    }

    public Double find_max_buy(){
        return buys.max_price();
    }

    public Double find_min_sell(){
        return sells.min_price();
    }

}
