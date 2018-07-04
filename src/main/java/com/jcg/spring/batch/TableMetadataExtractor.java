package com.jcg.spring.batch;

import lombok.Setter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

@Setter
public class TableMetadataExtractor implements Tasklet {

    private JdbcTemplate jdbcTemplate;


    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Map<String,String> tableColumnTypes = jdbcTemplate.query("Select * from cdi_buffer_ph where 1!=1", resultSet -> {
            ResultSetMetaData meta = resultSet.getMetaData();
            Map<String,String> resultMap = new HashMap<>();
            for (int i = 1; i<=meta.getColumnCount(); i++) {
                resultMap.put(meta.getColumnName(i).toUpperCase(), meta.getColumnClassName(i));
            }
            return resultMap;

        });
        ExecutionContext context= chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        context.put("table_meta", tableColumnTypes);
        return RepeatStatus.FINISHED;
    }
}
