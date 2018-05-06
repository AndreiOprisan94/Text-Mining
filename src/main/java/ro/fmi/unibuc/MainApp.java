package ro.fmi.unibuc;


import ro.fmi.unibuc.ml.sentimentanalysis.SentimentAnalysisAppStarter;

public final class MainApp {
    public static void main(String[] args) {
        final SentimentAnalysisAppStarter appStarter = new SentimentAnalysisAppStarter();
        appStarter.startApp();
    }
}
