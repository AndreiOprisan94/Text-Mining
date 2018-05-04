package ro.fmi.unibuc.service.data;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

public final class TrainingResult {
    private final FilteredClassifier classifier;
    private final Instances data;

    private TrainingResult(FilteredClassifier classifier, Instances data) {
        this.classifier = classifier;
        this.data = data;
    }

    public FilteredClassifier getClassifier() {
        return classifier;
    }

    public Instances getData() {
        return data;
    }

    public static TrainingResult of(FilteredClassifier classifier, Instances data) {
        return new TrainingResult(classifier, data);
    }
}
