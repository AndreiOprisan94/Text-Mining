package ro.fmi.unibuc.ml.sentimentanalysis.persister;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.fmi.unibuc.ml.sentimentanalysis.service.data.EvaluationResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
final class PlainFileEvaluationPersister implements EvaluationResultPersister {
    private final Logger logger = LoggerFactory.getLogger(super.getClass());

    private final String positiveTestResultPath;
    private final String negativeTestResultPath;

    PlainFileEvaluationPersister(@Value("${positiveTestResultPath}") String positiveTestResultPath,
                                 @Value("${negativeTestResultPath}") String negativeTestResultPath) {
        this.positiveTestResultPath = positiveTestResultPath;
        this.negativeTestResultPath = negativeTestResultPath;
    }

    @Override
    public boolean persistPositiveTest(EvaluationResult evaluationResult) {
        logger.info("Writing POSITIVE test results to destination");
        return persist(evaluationResult, positiveTestResultPath);
    }

    @Override
    public boolean persistNegativeTest(EvaluationResult evaluationResult) {
        logger.info("Writing NEGATIVE test results to destination");
        return persist(evaluationResult, negativeTestResultPath);
    }

    private boolean persist(final EvaluationResult result, final String filePath) {
        try {
            final File file = new File(filePath);
            final Charset charset = StandardCharsets.UTF_8;
            FileUtils.write(file, result.toString(), charset);
        } catch (final IOException problemWritingTheResults) {
            logger.warn("Could not write results to destination: ", problemWritingTheResults.getMessage());
            return false;
        }

        return true;
    }
}
