package com.jcg.spring.batch;

import lombok.Setter;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
@Setter
public class SkipRowPolicy implements SkipPolicy {

    private int skipLimit;
    @Override
    public boolean shouldSkip(Throwable throwable, int i) throws SkipLimitExceededException {
        if (i < skipLimit) {
            System.out.println(throwable.toString() + " " + i);
            return true;}
        return false;
    }
}
