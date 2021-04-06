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

    public void sendBuyOrder(double size){
        //create new order
        Order newOrder = new Order("buy", size, Double.POSITIVE_INFINITY);
        while (size > 0 && buys.getDepth() > 0){
            LinkedList<Order> curList = buys.maxPriceList();
            int i = 0;
            while (i < curList.size() && size > 0) {
                Order curOrder = curList.get(i);
                double curQuantity = curOrder.getQuantity();
                if (size > curQuantity){
                    size -= curOrder.getQuantity();
                    // delete order
                    buys.deleteOrder(curOrder.getTradeId());
                    Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);

                } else {
                    curOrder.setQuantity(curQuantity - size);
                    Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);
                }
                i++;
            }
        }
        if (size > 0){
            newOrder.setPrice(buys.maxPrice());
            buys.addOrder(newOrder);
        }

    }


    public void sendSellOrder(double size){
        Order newOrder = new Order("sell", size, -Double.POSITIVE_INFINITY);

        while (size > 0 && sells.getDepth() > 0){
            LinkedList<Order> curList = sells.minPriceList();
            int i = 0;
            while (i < curList.size() && size > 0) {
                Order curOrder = curList.get(i);
                double curQuantity = curOrder.getQuantity();
                if (size > curQuantity){
                    size -= curOrder.getQuantity();
                    // delete order
                    sells.deleteOrder(curOrder.getTradeId());
                    Logger.Log(newOrder.getTradeId(), "sell", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "buy", curOrder.getPrice(), size); //check log timing here
                } else {
                    curOrder.setQuantity(curQuantity - size);
                    Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);
                }
                i++;
            }
        }
        if (size > 0){
            newOrder.setPrice(sells.minPrice());
            sells.addOrder(newOrder);
        }
    }

    public Double find_max_buy(){
        return buys.maxPrice();
    }

    public Double find_min_sell(){
        return sells.minPrice();
    }

}
