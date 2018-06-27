package com.jcg.spring.batch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class CsvRow {
    private String ssid;
    private String sscd;
    private Date date;
}
