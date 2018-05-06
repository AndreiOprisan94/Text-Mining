package ro.fmi.unibuc.ml.sentimentanalysis.service.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import weka.core.stemmers.LovinsStemmer;
import weka.core.stemmers.Stemmer;
import weka.core.stopwords.Rainbow;
import weka.core.stopwords.StopwordsHandler;
import weka.core.tokenizers.Tokenizer;
import weka.core.tokenizers.WordTokenizer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Configuration
class SentimentAnalyzerConfiguration {
    @Value("${positiveFilesPath}")
    private String positiveFilesPath;

    @Value("${negativeFilesPath}")
    private String negativeFilesPath;

    @Value("${positiveTestPath}")
    private String positiveTestPath;

    @Value("${negativeTestPath}")
    private String negativeTestPath;

    @Bean
    public List<File> positiveFiles() {
        final File positiveDir = new File(positiveFilesPath);
        final List<File> files = Arrays.asList(positiveDir.listFiles());

        return files;
    }

    @Bean
    public List<File> negativeFiles() {
        final File negativeDir = new File(negativeFilesPath);
        final List<File> files = Arrays.asList(negativeDir.listFiles());

        return files;
    }

    @Bean
    public List<File> positiveTestFiles() {
        final File positiveTestDir = new File(positiveTestPath);
        final List<File> files = Arrays.asList(positiveTestDir.listFiles());

        return files;
    }

    @Bean
    public List<File> negativeTestFiles() {
        final File negativeTestDir = new File(negativeTestPath);
        final List<File> files = Arrays.asList(negativeTestDir.listFiles());

        return files;
    }

    @Bean
    public String fileDelimiter() {
        return "\\A";
    }

    @Bean
    public Double denseInstanceWeight() {
        return 1.0d;
    }

    @Bean
    public StopwordsHandler stopwords() {
        return new Rainbow();
    }

    @Bean
    public Stemmer stemmer() {
        return new LovinsStemmer();
    }

    @Bean
    public Tokenizer tokenizer() {
        return new WordTokenizer();
    }

}
