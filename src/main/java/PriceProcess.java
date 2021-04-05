import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class PriceProcess {
    double price;
    Instant last_time;

    PriceProcess(){
        System.out.println("Starting Price Process...");
        price = 0;
        last_time = Instant.now();
    }

    double query_price(){
        Instant cur_time = Instant.now();
        Duration res = Duration.between(last_time, cur_time);
        Random rn = new Random();
        int maximum = (int) res.getSeconds();
        int minimum = -maximum;
        int range = maximum - minimum + 1;
        int randNum = rn.nextInt(range) + minimum;
        price += randNum*(0.014/Math.sqrt(86400)); //average daily volatility of s&p500 is 1.4%, then divided by sqrt of time
        return price;
    }

}
