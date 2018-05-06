package ro.fmi.unibuc.service;

import org.springframework.beans.factory.annotation.Autowired;

abstract class AbstractSentimentAnalyzerService {
    @Autowired protected String fileDelimiter;
    @Autowired protected Double denseInstanceWeight;
}
