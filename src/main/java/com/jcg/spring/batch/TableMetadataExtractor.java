package com.jcg.spring.batch;

import lombok.Setter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
@Setter
public class TableMetadataExtractor implements Tasklet {

    private JdbcTemplate jdbcTemplate;

    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        JdbcUtils.extractDatabaseMetaData(jdbcTemplate.getDataSource(), meta -> {
            ResultSet rs = meta.getColumns(null,null,"batch_job_execution", "");
            rs.next();
            return null;
            //java.sql.Types.BIGINT;
        });
        return null;
    }
}
