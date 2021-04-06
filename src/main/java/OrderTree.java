import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class OrderTree {
    TreeMap<Double, LinkedList<Order>> priceTree = new TreeMap<>();
    HashMap<Double, LinkedList<Order>> priceMap = new HashMap<>();
    HashMap<Integer, Order> orderMap = new HashMap<>();
    int depth;
    int number_of_orders;


    public OrderTree(){
        reset();
    }

    public void reset(){
        priceTree.clear();
        priceMap.clear();
        depth = 0;
        number_of_orders = 0;
    }

    public void add_order(Order quote){
        number_of_orders += 1;
        double q_price = quote.getPrice();
        if (!priceMap.containsKey(q_price)){
            depth += 1;
            LinkedList<Order> queue = new LinkedList<>();
            priceTree.put(q_price, queue);
            priceMap.put(q_price, queue);
        }
        priceMap.get(q_price).add(quote);
        orderMap.put(quote.getTradeId(), quote);
    }

    public void delete_order(int id){
        Order order = orderMap.get(id);
        if (order != null){
            number_of_orders -= 1;
        }
        LinkedList<Order> ll = priceTree.get(order.getPrice());
        for (int i = 0; i < ll.size(); i++){
            Order order1 = ll.get(i);
            if (order1.getTradeId() == id){
                ll.remove(order1);
                break;
            }
        }
        if (ll.size()==0){
            priceTree.remove(order.getPrice());
            priceMap.remove(order.getPrice());
        }
        orderMap.remove(id);
    }

    public void update_order(int id, double quantity){
        Order order = orderMap.get(id);
        if (order == null){
            return;
        }
        LinkedList<Order> ll = priceTree.get(order.getPrice());
        for (int i = 0; i < ll.size(); i++){
            Order order2 = ll.get(i);
            if (order2.getTradeId() == id){
                order2.setQuantity(quantity);
                break;
            }
        }
    }

    public Double max_price(){
        if (depth>0){
            return priceTree.lastKey();
        } else {
            return null;
        }
    }

    public Double min_price(){
        if (depth>0){
            return priceTree.lastKey();
        } else {
            return null;
        }
    }

    public LinkedList get_quote_list(double price){
        return priceMap.get(price);
    }

    public LinkedList max_price_list(){
        if (depth>0){
            return get_quote_list(max_price());
        } else {
            return null;
        }
    }

    public LinkedList min_price_list(){
        if (depth>0){
            return get_quote_list(min_price());
        } else {
            return null;
        }
    }
}
