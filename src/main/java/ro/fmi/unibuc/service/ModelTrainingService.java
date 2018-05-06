package ro.fmi.unibuc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.fmi.unibuc.service.factory.SentimentAnalyzerFactory;
import ro.fmi.unibuc.service.data.TrainingResult;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

@Service
public final class ModelTrainingService {
    private static final Logger logger = LoggerFactory.getLogger(ModelTrainingService.class);
    private static final String delimiter = "\\A";
    private static final double denseInstanceWeight = 1.0;

    private final SentimentAnalyzerFactory factory;
    private final List<File> positiveFiles;
    private final List<File> negativeFiles;

    public ModelTrainingService(SentimentAnalyzerFactory factory, List<File> positiveFiles, List<File> negativeFiles) {
        this.factory = factory;
        this.positiveFiles = positiveFiles;
        this.negativeFiles = negativeFiles;
    }

    public TrainingResult trainModel() {
        logger.info("Training Model with {} of negative files and {} of positive files", negativeFiles.size(), positiveFiles.size());

        final Instances data = addDenseInstances(factory.createData());

        final StringToWordVector filter = factory.createFilter(data);
        final FilteredClassifier classifier = factory.createClassifier(data, filter);

        logger.info("Model Build with success!");

        return TrainingResult.of(classifier, data);
    }

    private Instances addDenseInstances(Instances data) {
        data = addDenseInstances(positiveFiles, data, 1);
        data = addDenseInstances(negativeFiles, data, 0);

        data.setClassIndex(1);

        return data;
    }

    private Instances addDenseInstances(List<File> files, Instances data, int secondValue) {
        try {
            for (File file : files) {
                final Scanner scanner = new Scanner(file).useDelimiter(delimiter);
                final String fileContent = scanner.next();
                double[] value = new double[data.numAttributes()];
                value[0] = data.attribute(0).addStringValue(fileContent);
                value[1] = secondValue;
                final DenseInstance denseInstance = new DenseInstance(denseInstanceWeight, value);
                data.add(denseInstance);
                data.setClassIndex(1);
                scanner.close();
            }
        } catch (final FileNotFoundException problemOpeningFile) {
            logger.warn("Problem opening file encountered {} ", problemOpeningFile.getMessage());
            return null;
        }
        return data;
    }
}
