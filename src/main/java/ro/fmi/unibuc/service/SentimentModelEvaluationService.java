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
public final class SentimentModelEvaluationService {
    private static final Logger logger = LoggerFactory.getLogger(SentimentModelEvaluationService.class);
    private static final String delimiter = "\\A";
    private static final double denseInstanceWeight = 1.0;

    private final List<File> positiveTestFile;
    private final List<File> negativeTestFiles;

    public SentimentModelEvaluationService(List<File> positiveTestFiles, List<File> negativeTestFiles) {
        this.positiveTestFile = positiveTestFiles;
        this.negativeTestFiles = negativeTestFiles;
    }

    public EvaluationResult evaluatePositive(TrainingResult result) {
        logger.info("Evaluating positive test data with {} test files", positiveTestFile.size());
        final EvaluationResult.Builder builder = EvaluationResult.builder();
        int truePositives = 0;
        int falseNegative = 0;

        try {
            for (File file : positiveTestFile) {
                final Scanner scanner = new Scanner(file).useDelimiter(delimiter);
                final String fileContent = scanner.next();

                final Instances data  = result.getData();
                double[] value = new double[data.numAttributes()];
                value[0] = data.attribute(0).addStringValue(fileContent);

                final Instance classificationInstance = new DenseInstance(denseInstanceWeight, value);
                data.setClassIndex(1);
                classificationInstance.setDataset(data);

                final double prediction = result.getClassifier().classifyInstance(classificationInstance);

                if (prediction == 0) {
                    falseNegative++;
                    builder.withResult(file.getName(), NEGATIVE);
                } else {
                    truePositives++;
                    builder.withResult(file.getName(), POSITIVE);
                }

                scanner.close();
            }
        } catch (final Exception problem) {
            logger.warn("Problem opening file {}", problem.getMessage());
            return null;
        }

        final EvaluationResult evaluationResult = builder.build();

        logger.info("From {} positive test files, there were {} predicted as POSITIVE and {} predicted as NEGATIVE",
                positiveTestFile.size(), evaluationResult.getNumberOfPositives(), evaluationResult.getNumberOfNegatives());

        return evaluationResult;
    }
}