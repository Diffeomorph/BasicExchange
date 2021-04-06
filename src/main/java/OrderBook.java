import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class OrderBook {
    private static int MIN_TRADE_ID = 0;
    private OrderTree buys = new OrderTree();
    private OrderTree sells = new OrderTree();

    OrderBook(){
        reset();
    }

    public void reset(){
        buys.reset();
        sells.reset();
        MIN_TRADE_ID = 0;
    }

    //Limit order to buy
    public void sendBuyLimitOrder(double size, double limit){
        //create new order
        Order newOrder = new Order("buy", size, limit);

        while (size > 0 && sells.getDepth() > 0 && limit >= sells.minPrice() && sells.minPrice()!= null){
            LinkedList<Order> curList = sells.minPriceList();
            int i = 0;
            while (i < curList.size() && size > 0) {
                Order curOrder = curList.get(i);
                double curQuantity = curOrder.getQuantity();
                if (size >= curQuantity){
                    size -= curOrder.getQuantity();
                    // delete order
                    System.out.println(curOrder.getTradeId());
                    sells.deleteOrder(curOrder.getTradeId());
                    Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);

                } else {
                    curOrder.setQuantity(curQuantity - size);
                    size = 0;
                    Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);
                }
                i++;
            }
        }
        //leave remainder on the book
        if (size > 0){
            buys.addOrder(newOrder);
        }
    }

    //Limit order to sell
    public void sendSellLimitOrder(double size, double limit){
        Order newOrder = new Order("sell", size, limit);

        while (size > 0 && buys.getDepth() > 0 && buys.maxPrice() >= limit && buys.maxPrice()!= null){
            LinkedList<Order> curList = buys.maxPriceList();
            int i = 0;
            while (i < curList.size() && size > 0) {
                Order curOrder = curList.get(i);
                double curQuantity = curOrder.getQuantity();
                if (size >= curQuantity){
                    size -= curOrder.getQuantity();
                    // delete order
                    System.out.println(curOrder.getTradeId());
                    buys.deleteOrder(curOrder.getTradeId());
                    Logger.Log(newOrder.getTradeId(), "sell", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "buy", curOrder.getPrice(), size); //check log timing here
                } else {
                    curOrder.setQuantity(curQuantity - size);
                    size = 0;
                    Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);
                }
                i++;
            }
        }
        //leave remainder on the book
        if (size > 0){
            sells.addOrder(newOrder);
        }
    }

    //Market order to buy
    public void sendBuyOrder(double size){
        //create new order
        Order newOrder = new Order("buy", size, Double.POSITIVE_INFINITY);
        while (size > 0 && sells.getDepth() > 0){
            LinkedList<Order> curList = sells.minPriceList();
            int i = 0;
            while (i < curList.size() && size > 0) {
                Order curOrder = curList.get(i);
                double curQuantity = curOrder.getQuantity();
                if (size >= curQuantity){
                    size -= curOrder.getQuantity();
                    // delete order
                    sells.deleteOrder(curOrder.getTradeId());
                    Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);

                } else {
                    curOrder.setQuantity(curQuantity - size);
                    size = 0;
                    Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);
                }
                i++;
            }
        }
    }

    //Market order to sell
    public void sendSellOrder(double size){
        Order newOrder = new Order("sell", size, -Double.POSITIVE_INFINITY);

        while (size > 0 && buys.getDepth() > 0){
            LinkedList<Order> curList = buys.maxPriceList();
            int i = 0;
            while (i < curList.size() && size > 0) {
                Order curOrder = curList.get(i);
                double curQuantity = curOrder.getQuantity();
                if (size >= curQuantity){
                    size -= curOrder.getQuantity();
                    // delete order
                    buys.deleteOrder(curOrder.getTradeId());
                    Logger.Log(newOrder.getTradeId(), "sell", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "buy", curOrder.getPrice(), size); //check log timing here
                } else {
                    curOrder.setQuantity(curQuantity - size);
                    size = 0;
                    Logger.Log(newOrder.getTradeId(), "buy", curOrder.getPrice(), size);
                    Logger.Log(curOrder.getTradeId(), "sell", curOrder.getPrice(), size);
                }
                i++;
            }
        }
    }

    public Double findMaxBuy(){
        return buys.maxPrice();
    }

    public Double findMinSell(){
        return sells.minPrice();
    }

    public static int getMinTradeId(){
        return MIN_TRADE_ID;
    }

    public static void setMinTradeId(int id){
        MIN_TRADE_ID = id;
    }

    public void printOrderBook(){
        System.out.println("ORDER BOOK");
        System.out.println("----------------------------------");
        System.out.println("BUYS:");
        TreeMap<Double, LinkedList<Order>> buysPriceTree = buys.getPriceMap();
        for (Map.Entry<Double, LinkedList<Order>> entry: buysPriceTree.entrySet()){
            for (int i = 0; i < entry.getValue().size(); i++){
                System.out.println("ID: "+ entry.getValue().get(i).getTradeId() + " , " + "Price: " + entry.getValue().get(i).getPrice() + " , " + "Quantity: " + entry.getValue().get(i).getQuantity());
            }
            System.out.println("---------------------------------------");
        }

        System.out.println("---------------------------------------");
        System.out.println("SELLS:");

        TreeMap<Double, LinkedList<Order>> sellsPriceTree = sells.getPriceMap();
        for (Map.Entry<Double, LinkedList<Order>> entry: sellsPriceTree.entrySet()){
            for (int i = 0; i < entry.getValue().size(); i++){
                System.out.println("ID: " + entry.getValue().get(i).getTradeId() + " , " +"Price: " + entry.getValue().get(i).getPrice() + " , " + "Quantity: " + entry.getValue().get(i).getQuantity());
            }
            System.out.println("---------------------------------------");
        }

    }

}
