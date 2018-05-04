package ro.fmi.unibuc;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.fmi.unibuc.service.ModelTrainingService;
import ro.fmi.unibuc.service.SentimentModelEvaluationService;
import ro.fmi.unibuc.service.data.EvaluationResult;
import ro.fmi.unibuc.service.data.TrainingResult;

@SpringBootApplication
public class MainApp implements CommandLineRunner {
    private final ModelTrainingService trainingService;
    private final SentimentModelEvaluationService evaluationService;

    public MainApp(ModelTrainingService trainingService, SentimentModelEvaluationService evaluationService) {
        this.trainingService = trainingService;
        this.evaluationService = evaluationService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Hello Text Mining World");

        final TrainingResult trainingResult =  trainingService.trainModel();
        final EvaluationResult evaluationResult = evaluationService.evaluatePositive(trainingResult);

        System.out.println("Thank you!");
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
}
