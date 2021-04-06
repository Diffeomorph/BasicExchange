import com.sun.deploy.nativesandbox.NativeSandboxOutputStream;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.FileWriter;
        import java.io.IOException;
        import java.time.Instant;

public class Logger {

    public Logger() throws IOException {
    }


    public static void Log(String direction, double price, double quantity) {
        try{
            String price_str = Double.toString(price);
            String quantity_str = Double.toString(quantity);
            Instant now = Instant.now();
            FileWriter myWriter = new FileWriter("logfile.txt", true);
            myWriter.write("buy/sell: " + direction +  " , " + "price: "+ price_str + " , " + "quantity: " + quantity_str + " , " + "now: " + now);
        } catch (IOException e){
            e.printStackTrace();
        }


    }
}


