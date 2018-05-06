package ro.fmi.unibuc.ml.sentimentanalysis.service.data;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.LF;

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

    @Override
    public String toString() {
        return sentimentMap.entrySet().stream()
                .map(this::makeEntryString)
                .reduce(EMPTY, (lhs, rhs) -> lhs.concat(LF).concat(rhs).concat(LF));
    }

    private String makeEntryString(final Map.Entry<String, Sentiment> entry) {
        final String format = "Review %s was predicted as %s";

        return String.format(format, entry.getKey(), entry.getValue().toString());
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
