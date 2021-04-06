
import java.io.FileWriter;
        import java.io.IOException;
        import java.time.Instant;

public class Logger {

    public Logger() throws IOException { }

    public static void Log(int id, String direction, double price, double quantity) {
        try{
            String price_str = Double.toString(price);
            String quantity_str = Double.toString(quantity);
            Instant now = Instant.now();
            FileWriter myWriter = new FileWriter("logfile.txt", true);
            myWriter.write("Id: "+ id  + " , " + "Buy/Sell: " + direction +  " , " + "Price: "+ price_str + " , " + "Quantity: " + quantity_str + " , " + "Time: " + now);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}


