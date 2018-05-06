package ro.fmi.unibuc.ml.sentimentanalysis.model;

import ro.fmi.unibuc.ml.sentimentanalysis.service.data.EvaluationResult;

public final class SentimentAnalysisProjectResult {
    private final EvaluationResult positiveTestResults;
    private final EvaluationResult negativeTestResults;

    private SentimentAnalysisProjectResult(EvaluationResult positiveTestResults, EvaluationResult negativeTestResults) {
        this.positiveTestResults = positiveTestResults;
        this.negativeTestResults = negativeTestResults;
    }

    public float getPositiveTestsAccuracy() {
        final float truePositives = positiveTestResults.getNumberOfPositives();
        final float falseNegatives = positiveTestResults.getNumberOfNegatives();
        final float totalRecords = Float.sum(truePositives, falseNegatives);

        return (100f * truePositives) / totalRecords;
    }

    public float getNegativeTestsAccuracy() {
        final float trueNegatives = negativeTestResults.getNumberOfNegatives();
        final float falsePositives = negativeTestResults.getNumberOfPositives();
        final float totalRecords = Float.sum(trueNegatives, falsePositives);

        return (100f * trueNegatives) / totalRecords;
    }

    public static SentimentAnalysisProjectResult of(final EvaluationResult positiveTestResults,
                                                    final EvaluationResult negativeTestResults) {
        return new SentimentAnalysisProjectResult(positiveTestResults, negativeTestResults);
    }
}
