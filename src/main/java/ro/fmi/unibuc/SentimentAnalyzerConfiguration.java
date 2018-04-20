package ro.fmi.unibuc;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import weka.core.stemmers.LovinsStemmer;
import weka.core.stopwords.Rainbow;
import weka.core.tokenizers.WordTokenizer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SentimentAnalyzerConfiguration {
    @Value("${positiveFilesPath}")
    private String positiveFilesPath;

    @Value("${negativeFilesPath}")
    private String negativeFilesPath;


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
    public Rainbow stopwords() {
        return new Rainbow();
    }

    @Bean
    public LovinsStemmer stemmer() {
        return new LovinsStemmer();
    }

    @Bean
    public WordTokenizer tokenizer() {
        return new WordTokenizer();
    }
}
