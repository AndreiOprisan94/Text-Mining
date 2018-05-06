package ro.fmi.unibuc.ml.sentimentanalysis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ro.fmi.unibuc.ml.sentimentanalysis.persister.EvaluationResultPersister;
import ro.fmi.unibuc.ml.sentimentanalysis.service.ModelTrainingService;
import ro.fmi.unibuc.ml.sentimentanalysis.service.SentimentModelEvaluationService;
import ro.fmi.unibuc.ml.sentimentanalysis.service.data.EvaluationResult;
import ro.fmi.unibuc.ml.sentimentanalysis.service.data.TrainingResult;

@SpringBootApplication
@ComponentScan(basePackages = "ro.fmi.unibuc.ml.sentimentanalysis")
class SentimentAnalysisApp implements CommandLineRunner {
    private final ModelTrainingService trainingService;
    private final SentimentModelEvaluationService evaluationService;
    private final EvaluationResultPersister persister;

    SentimentAnalysisApp(ModelTrainingService trainingService, SentimentModelEvaluationService evaluationService, EvaluationResultPersister persister) {
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
}
