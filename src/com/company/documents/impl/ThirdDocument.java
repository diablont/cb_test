package com.company.documents.impl;

import com.company.documents.AbstractDocument;
import com.company.documents.PaperSize;

import static com.company.documents.Type.RECORDS;

public class ThirdDocument extends AbstractDocument {
	public ThirdDocument(Integer id) {
		super(id, RECORDS, 4, new PaperSize(10, 10));
	}
}
