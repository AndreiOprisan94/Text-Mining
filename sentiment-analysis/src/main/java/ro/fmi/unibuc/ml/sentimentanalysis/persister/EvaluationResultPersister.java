package ro.fmi.unibuc.ml.sentimentanalysis.persister;

import ro.fmi.unibuc.ml.sentimentanalysis.service.data.EvaluationResult;

public interface EvaluationResultPersister {
    boolean persistPositiveTest(EvaluationResult evaluationResult);
    boolean persistNegativeTest(EvaluationResult evaluationResult);
}
