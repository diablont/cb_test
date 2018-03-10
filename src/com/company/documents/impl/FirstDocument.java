package com.company.documents.impl;

import com.company.documents.AbstractDocument;
import com.company.documents.PaperSize;

import static com.company.documents.Type.BILL;

public class FirstDocument extends AbstractDocument {

	public FirstDocument(Integer id) {
		super(id, BILL, 2, new PaperSize(24, 26));
	}
}
