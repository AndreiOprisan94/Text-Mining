package ro.fmi.unibuc.ml.sentimentanalysis;

import org.springframework.boot.SpringApplication;

public final class SentimentAnalysisAppStarter {
    public void startApp() {
        SpringApplication.run(SentimentAnalysisApp.class, emptyStringArray());
    }

    private static String[] emptyStringArray() {
        return new String[0];
    }
}
