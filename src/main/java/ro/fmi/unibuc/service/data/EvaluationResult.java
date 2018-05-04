package ro.fmi.unibuc.service.data;

import java.util.HashMap;
import java.util.Map;

public final class EvaluationResult {
    private final Map<String, Sentiment> sentimentMap;

    private EvaluationResult(Map<String, Sentiment> sentimentMap) {
        this.sentimentMap = sentimentMap;
    }

    public long getNumberOfPositives() {
        return sentimentMap.values().stream()
                .filter(sentiment -> sentiment.equals(Sentiment.POSITIVE))
                .count();
    }

    public long getNumberOfNegatives() {
        return sentimentMap.values().stream()
                .filter(sentiment -> sentiment.equals(Sentiment.NEGATIVE))
                .count();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final Map<String, Sentiment> sentimentMap = new HashMap<>();

        private Builder() {
        }

        public Builder withResult(String fileName, Sentiment sentiment) {
            this.sentimentMap.put(fileName, sentiment);
            return this;
        }

        public EvaluationResult build() {
            return new EvaluationResult(this.sentimentMap);
        }
    }

    public enum Sentiment {
        POSITIVE,
        NEGATIVE
    }
}
