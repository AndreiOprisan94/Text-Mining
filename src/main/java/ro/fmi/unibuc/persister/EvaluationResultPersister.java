package ro.fmi.unibuc.persister;

import ro.fmi.unibuc.service.data.EvaluationResult;

public interface EvaluationResultPersister {
    boolean persistPositiveTest(EvaluationResult evaluationResult);
    boolean persistNegativeTest(EvaluationResult evaluationResult);
}
