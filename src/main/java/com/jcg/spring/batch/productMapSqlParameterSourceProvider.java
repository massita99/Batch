package com.jcg.spring.batch;


import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class ProductMapSqlParameterSourceProvider<T extends Report> implements ItemSqlParameterSourceProvider<T> {

    public static final String SQL = "INSERT INTO PRODUCT (ID, NAME, DATE) VALUES(:id, :staffName, :date)";
    @Override
    public SqlParameterSource createSqlParameterSource(T t) {
        return new MapSqlParameterSource()
                .addValue("id", t.getId())
                .addValue("staffName", t.getStaffName())
                .addValue("date", t.getDate());
    }
}
