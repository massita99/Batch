package com.jcg.spring.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CsvRowFieldSetMapper implements FieldSetMapper<CsvRow> {

	static CsvRow csvRowObj;
	private SimpleDateFormat dateFormatObj = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

	public CsvRow mapFieldSet(FieldSet fieldSetObj) throws BindException {
		csvRowObj = new CsvRow();
		csvRowObj.setSsid(fieldSetObj.readString(1));
		csvRowObj.setSscd(fieldSetObj.readString(2));
		String csvDate = fieldSetObj.readString(3);
		try {
			csvRowObj.setDate(dateFormatObj.parse(csvDate));
		} catch (ParseException parseExceptionObj) {
			parseExceptionObj.printStackTrace();
		}
		return csvRowObj;
	}
}