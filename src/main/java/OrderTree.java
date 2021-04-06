import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class OrderTree {
    private TreeMap<Double, LinkedList<Order>> priceTree = new TreeMap<>();
    private HashMap<Double, LinkedList<Order>> priceMap = new HashMap<>();
    private HashMap<Integer, Order> orderMap = new HashMap<>();
    private int depth;

    public OrderTree(){
        reset();
    }

    public void reset(){
        this.priceTree.clear();
        this.priceMap.clear();
        this.depth = 0;
    }

    public void addOrder(Order quote){
        double qPrice = quote.getPrice();
        if (!priceMap.containsKey(qPrice)){
            depth += 1;
            LinkedList<Order> queue = new LinkedList<>();
            priceTree.put(qPrice, queue);
            priceMap.put(qPrice, queue);
        }
        priceMap.get(qPrice).add(quote);
        orderMap.put(quote.getTradeId(), quote);
    }

    public void deleteOrder(int id){
        Order order = orderMap.get(id);

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

    public void updateOrder(int id, double quantity){
        Order order = orderMap.get(id);
        if (order == null){
            return;
        }
        LinkedList<Order> ll = priceTree.get(order.getPrice());
        for (Order curOrder : ll){
            if (curOrder.getTradeId() == id){
                curOrder.setQuantity(quantity);
                break;
            }
        }
    }

    public Double maxPrice(){
        if (depth>0){
            return priceTree.lastKey();
        } else {
            return null;
        }
    }

    public Double minPrice(){
        if (depth>0){
            return priceTree.lastKey();
        } else {
            return null;
        }
    }

    public LinkedList<Order> getQuoteList(double price){
        return priceMap.get(price);
    }

    public LinkedList<Order> maxPriceList(){
        if (depth>0){
            return getQuoteList(maxPrice());
        } else {
            return null;
        }
    }

    public LinkedList<Order> minPriceList(){
        if (depth>0){
            return getQuoteList(minPrice());
        } else {
            return null;
        }
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }
}
