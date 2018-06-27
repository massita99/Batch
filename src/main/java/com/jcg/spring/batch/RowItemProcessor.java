package com.jcg.spring.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.transform.FieldSet;

public class RowItemProcessor implements ItemProcessor<FieldSet, FieldSet> {

	public static int i = 0;
	public FieldSet process(FieldSet itemObj) throws Exception {

		/*System.out.println("Processing Item?= " + itemObj);*/
		System.out.println(i++);

		return (itemObj.readString("pt_external_id").isEmpty()) ? null : itemObj;
	}
}