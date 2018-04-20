package ro.fmi.unibuc;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import weka.classifiers.meta.FilteredClassifier;

@SpringBootApplication
public class MainApp implements CommandLineRunner {
    private final ModelTrainingService service;

    public MainApp(ModelTrainingService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        System.out.println("Hello Text Mining World");

        final FilteredClassifier classifier = service.trainModel();

        if (classifier != null) {
            System.out.println("SUCCESS!");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
}
