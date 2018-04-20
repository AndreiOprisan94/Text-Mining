package ro.fmi.unibuc;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello Text Mining World");
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
}
