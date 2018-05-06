package ro.fmi.unibuc.ml.sentimentanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import ro.fmi.unibuc.ml.sentimentanalysis.model.SentimentAnalysisProjectResult;

public final class SentimentAnalysisProject {
    public SentimentAnalysisProjectResult doProject() {
        final ApplicationContext applicationContext = SpringApplication.run(SentimentAnalysisApp.class, emptyStringArray());
        final SentimentAnalysisApp sentimentAnalysisApp = applicationContext.getBean(SentimentAnalysisApp.class);

        final SentimentAnalysisProjectResult result = sentimentAnalysisApp.runSentimentAnalysis();

        return result;
    }

    private static String[] emptyStringArray() {
        return new String[0];
    }
}
