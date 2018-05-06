package ro.fmi.unibuc;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.fmi.unibuc.persister.EvaluationResultPersister;
import ro.fmi.unibuc.service.ModelTrainingService;
import ro.fmi.unibuc.service.SentimentModelEvaluationService;
import ro.fmi.unibuc.service.data.EvaluationResult;
import ro.fmi.unibuc.service.data.TrainingResult;

@SpringBootApplication
public class MainApp implements CommandLineRunner {
    private final ModelTrainingService trainingService;
    private final SentimentModelEvaluationService evaluationService;
    private final EvaluationResultPersister persister;

    public MainApp(ModelTrainingService trainingService, SentimentModelEvaluationService evaluationService, EvaluationResultPersister persister) {
        this.trainingService = trainingService;
        this.evaluationService = evaluationService;
        this.persister = persister;
    }

    @Override
    public void run(String... args) {
        System.out.println("Hello Text Mining World");

        final TrainingResult trainingResult =  trainingService.trainModel();
        final EvaluationResult positiveTestResults = evaluationService.evaluatePositive(trainingResult);
        final EvaluationResult negativeTestResults = evaluationService.evaluateNegatives(trainingResult);

        if (persister.persistPositiveTest(positiveTestResults)) {
            System.out.println("Positive test results persisted successfully!");
        }

        if (persister.persistNegativeTest(negativeTestResults)) {
            System.out.println("Negative test results persisted successfully!");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
}
