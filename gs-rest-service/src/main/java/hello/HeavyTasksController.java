package hello;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


@RestController
public class HeavyTasksController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    final static int INDEX_SIZE = 500;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/prime")
    public Greeting prime(){

        LocalTime starTime = LocalTime.now();
        BigInteger bigInteger = new BigInteger(8000, new Random());
        bigInteger.nextProbablePrime();
        LocalTime endTime = LocalTime.now();

        return new Greeting(counter.incrementAndGet(), String.format("Request duration: %d seconds", ChronoUnit.SECONDS.between(starTime, endTime)));
    }

    @RequestMapping("/downloadfile")
    public Greeting downloadfile() throws IOException {       

        HttpDownloadUtility.downloadFile("http://ftp.vim.org/ftp/os/Linux/distr/porteus/i586/Porteus-v3.2.2/modules/07-printing-i586-02.12.2016.xzm", "/tmp");

        return new Greeting(counter.incrementAndGet(), "ok");
    }

    @RequestMapping("/stack")
    public Greeting stack() throws InterruptedException {

        FakeService s = new FakeService();

        s.start();
       
        return new Greeting(counter.incrementAndGet(), s.getCustomerList().stream().map(e -> e.toString()).reduce("", String::concat));
    }

    @RequestMapping("/allocate")
    public Greeting allocate() {
        float beforeMb = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024.0f/1024.0f;
		System.out.println("Before: "+beforeMb + "Mb");
		
		float afterMB = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024.0f / 1024.0f;
		
		System.out.println("After: "+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024.0f/1024.0f + "Mb");
        System.out.println("Diff: "+ (afterMB - beforeMb)+"Mb");
        
        return new Greeting(counter.incrementAndGet(), "ok");
    }
}
