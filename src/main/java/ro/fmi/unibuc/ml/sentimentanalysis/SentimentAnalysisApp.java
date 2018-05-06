package ro.fmi.unibuc.ml.sentimentanalysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ro.fmi.unibuc.ml.sentimentanalysis.model.SentimentAnalysisProjectResult;
import ro.fmi.unibuc.ml.sentimentanalysis.persister.EvaluationResultPersister;
import ro.fmi.unibuc.ml.sentimentanalysis.service.ModelTrainingService;
import ro.fmi.unibuc.ml.sentimentanalysis.service.SentimentModelEvaluationService;
import ro.fmi.unibuc.ml.sentimentanalysis.service.data.EvaluationResult;
import ro.fmi.unibuc.ml.sentimentanalysis.service.data.TrainingResult;

@SpringBootApplication
@ComponentScan(basePackages = "ro.fmi.unibuc.ml.sentimentanalysis")
class SentimentAnalysisApp {
    private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisApp.class);

    private final ModelTrainingService trainingService;
    private final SentimentModelEvaluationService evaluationService;
    private final EvaluationResultPersister persister;

    SentimentAnalysisApp(ModelTrainingService trainingService, SentimentModelEvaluationService evaluationService, EvaluationResultPersister persister) {
        this.trainingService = trainingService;
        this.evaluationService = evaluationService;
        this.persister = persister;
    }

    public SentimentAnalysisProjectResult runSentimentAnalysis() {
        logger.info("Starting sentiment analysis project");

        final TrainingResult trainingResult =  trainingService.trainModel();
        final EvaluationResult positiveTestResults = evaluationService.evaluatePositive(trainingResult);
        final EvaluationResult negativeTestResults = evaluationService.evaluateNegatives(trainingResult);

        if (persister.persistPositiveTest(positiveTestResults)) {
            logger.info("Positive test results persisted successfully!");
        }

        if (persister.persistNegativeTest(negativeTestResults)) {
            logger.info("Negative test results persisted successfully!");
        }

        return SentimentAnalysisProjectResult.of(positiveTestResults, negativeTestResults);
    }
}
