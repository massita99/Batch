package com.jcg.spring.batch;


import lombok.Setter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;

@Setter
public class CsvRowMapSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<FieldSet> {


    //private Map<String,Integer> tableMeta;
    private StepExecution context;

    public static final String SQL = "INSERT INTO NEWTABLE (SSID, SSCD, DATE) VALUES(:pt_external_id, :pt_source_system_cd, :pt_actual_date)";

    @Override

    public SqlParameterSource createSqlParameterSource(FieldSet t) {
        Map<String,Integer> tableHeader= (Map<String, Integer>) context.getJobExecution().getExecutionContext().get("table_meta");
        MapSqlParameterSource result = new MapSqlParameterSource();
        for (String columnName : t.getNames()) {
            if (tableHeader.containsKey(columnName.toUpperCase())) {
                /*if (tableHeader.get(columnName.toUpperCase()) == Types.TIMESTAMP)
                    result.addValue(columnName, t.readDate(columnName), tableHeader.get(columnName.toUpperCase()));
                else*/
                    result.addValue(columnName, t.readString(columnName), tableHeader.get(columnName.toUpperCase()));
            }
        }
        /*for (Map.Entry<String, Integer> line : tableHeader.entrySet()) {
            if (!t.) {
                result.addValue(line.getKey(), t.readString(line.getKey()), line.getValue());
            }
        }*/
        return result;
    }
}
