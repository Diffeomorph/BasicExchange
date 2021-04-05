import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;


public class OrderBook {
    public static int min_trade_id = 0;
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
        ArrayList<Integer> trades = new ArrayList<>();
        //while
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

                } else {
                    current_order.quantity = current_quantity - size;
                }
                i++;
            }
        }
        if (size > 0){
            Order new_order = new Order("buy", size, buys.max_price());
            buys.add_order(new_order);
        }

    }

    public void send_sell_order(double size){
        ArrayList<Integer> trades = new ArrayList<>();
        //while
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
                } else {
                    current_order.quantity = current_quantity - size;
                }
                i++;
            }
        }
        if (size > 0){
            Order new_order = new Order("sell", size, sells.min_price());
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
