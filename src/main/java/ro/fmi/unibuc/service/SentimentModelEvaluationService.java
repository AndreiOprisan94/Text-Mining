package ro.fmi.unibuc.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.fmi.unibuc.service.data.EvaluationResult;
import ro.fmi.unibuc.service.data.TrainingResult;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static ro.fmi.unibuc.service.data.EvaluationResult.Sentiment.NEGATIVE;
import static ro.fmi.unibuc.service.data.EvaluationResult.Sentiment.POSITIVE;

@Service
public final class SentimentModelEvaluationService extends AbstractSentimentAnalyzerService {
    private static final Logger logger = LoggerFactory.getLogger(SentimentModelEvaluationService.class);

    private final List<File> positiveTestFile;
    private final List<File> negativeTestFiles;

    SentimentModelEvaluationService(List<File> positiveTestFiles, List<File> negativeTestFiles) {
        this.positiveTestFile = positiveTestFiles;
        this.negativeTestFiles = negativeTestFiles;
    }

    public EvaluationResult evaluatePositive(TrainingResult trainingResult) {
        logger.info("Evaluating POSITIVE test data with {} test files", positiveTestFile.size());

        final EvaluationResult evaluationResult = evaluate(trainingResult, positiveTestFile);

        logger.info("From {} POSITIVE test files, there were {} predicted as POSITIVE and {} predicted as NEGATIVE",
                positiveTestFile.size(), evaluationResult.getNumberOfPositives(), evaluationResult.getNumberOfNegatives());

        return evaluationResult;
    }

    public EvaluationResult evaluateNegatives(TrainingResult trainingResult) {
        logger.info("Evaluating NEGATIVE test data with {} test files", negativeTestFiles.size());

        final EvaluationResult evaluationResult = evaluate(trainingResult, negativeTestFiles);

        logger.info("From {} NEGATIVE test files, there were {} predicted as NEGATIVE and {} predicted as POSITIVE",
                negativeTestFiles.size(), evaluationResult.getNumberOfNegatives(), evaluationResult.getNumberOfPositives());

        return evaluationResult;
    }

    private EvaluationResult evaluate(final TrainingResult trainingResult, final List<File> testFiles) {
        final EvaluationResult.Builder builder = EvaluationResult.builder();

        try {
            for (File file : testFiles) {
                final Scanner scanner = new Scanner(file).useDelimiter(fileDelimiter);
                final String fileContent = scanner.next();

                final Instances data = trainingResult.getData();
                double[] value = new double[data.numAttributes()];
                value[0] = data.attribute(0).addStringValue(fileContent);

                final Instance classificationInstance = new DenseInstance(denseInstanceWeight, value);
                data.setClassIndex(1);
                classificationInstance.setDataset(data);

                final double prediction = trainingResult.getClassifier().classifyInstance(classificationInstance);

                if (prediction == 0) {
                    builder.withResult(file.getName(), NEGATIVE);
                } else {
                    builder.withResult(file.getName(), POSITIVE);
                }

                scanner.close();
            }
        } catch (final Exception problem) {
            logger.warn("Problem opening file {}", problem.getMessage());
            return null;
        }

        final EvaluationResult evaluationResult = builder.build();

        return evaluationResult;
    }
}
