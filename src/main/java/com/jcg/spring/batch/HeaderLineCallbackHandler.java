package com.jcg.spring.batch;

import lombok.Setter;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

/**
 * Class for adding csv header to FieldSet
 * @author maximk
 */

@Setter
public class HeaderLineCallbackHandler implements LineCallbackHandler {

    private DelimitedLineTokenizer lineTokenizer;

    public void handleLine(String s) {
        lineTokenizer.setNames(lineTokenizer.tokenize(s).getValues());
    }
}
