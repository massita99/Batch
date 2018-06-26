package com.jcg.spring.batch;

import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class HeaderLineCallbackHandler implements LineCallbackHandler {

    public void setLineTokenizer(DelimitedLineTokenizer lineTokenizer) {
        this.lineTokenizer = lineTokenizer;
    }

    private DelimitedLineTokenizer lineTokenizer;


    public void handleLine(String s) {
        lineTokenizer.setNames(lineTokenizer.tokenize(s).getValues());
    }
}
