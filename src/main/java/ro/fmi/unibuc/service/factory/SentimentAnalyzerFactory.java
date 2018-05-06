package ro.fmi.unibuc.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.stemmers.LovinsStemmer;
import weka.core.stemmers.Stemmer;
import weka.core.stopwords.Rainbow;
import weka.core.stopwords.StopwordsHandler;
import weka.core.tokenizers.Tokenizer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public final class SentimentAnalyzerFactory {
    private static final Logger logger = LoggerFactory.getLogger(SentimentAnalyzerFactory.class);

    private final StopwordsHandler stopwords;
    private final Stemmer stemmer;
    private final Tokenizer tokenizer;

    public SentimentAnalyzerFactory(StopwordsHandler stopwords, Stemmer stemmer, Tokenizer tokenizer) {
        this.stopwords = stopwords;
        this.stemmer = stemmer;
        this.tokenizer = tokenizer;
    }

    public Instances createData() {
        final List<String> sentiment = Arrays.asList("NEGATIVE", "POSITIVE");
        final Attribute contentAttribute = new Attribute("content", nullStringList());
        final Attribute sentimentAttribute = new Attribute("sentiment", sentiment);

        final ArrayList<Attribute> attributes = asArrayList(contentAttribute, sentimentAttribute);

        return new Instances("Instances", attributes, 0);
    }

    public StringToWordVector createFilter(Instances data) {
        try {
            final StringToWordVector filter = new StringToWordVector();

            filter.setAttributeIndices("first");
            filter.setLowerCaseTokens(true);
            filter.setInputFormat(data);
            filter.setStopwordsHandler(stopwords);
            filter.setStemmer(stemmer);
            filter.setTokenizer(tokenizer);

            return filter;

        } catch (final Exception somethingWentWrong) {
            logger.error("Something went wrong creating the filter {}", somethingWentWrong.getMessage());
            return null;
        }
    }

    public FilteredClassifier createClassifier(Instances data, StringToWordVector filter) {
        final FilteredClassifier classifier = new FilteredClassifier();
        final Classifier naiveBayesMultinomial = new NaiveBayes();
        classifier.setFilter(filter);
        classifier.setClassifier(naiveBayesMultinomial);

        logger.info("Building Naive Bayes Classifier");

        try {
            classifier.buildClassifier(data);
        } catch (final Exception somethingWentWrong) {
            logger.error("Something went wrong wen building Naive Bayes Classifier");
            return null;
        }

        return classifier;
    }


    private List<String> nullStringList() {
        return null;
    }

    private ArrayList<Attribute> asArrayList(Attribute... attributes) {
        final ArrayList<Attribute>  list = new ArrayList<>();

        for (Attribute attribute : attributes) {
            list.add(attribute);
        }

        return list;
    }
}
