package ro.fmi.unibuc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fmi.unibuc.ml.sentimentanalysis.SentimentAnalysisProject;
import ro.fmi.unibuc.ml.sentimentanalysis.model.SentimentAnalysisProjectResult;

public final class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        final SentimentAnalysisProject analysisProject = new SentimentAnalysisProject();
        final SentimentAnalysisProjectResult result = analysisProject.doProject();

        final float positiveTestAccuracy = result.getPositiveTestsAccuracy();
        final float negativeTestAccuracy = result.getNegativeTestsAccuracy();

        logger.info("The model build had {} accuracy on POSITIVE test data and {} accuracy on NEGATIVE test data!", positiveTestAccuracy, negativeTestAccuracy);

    }
}
