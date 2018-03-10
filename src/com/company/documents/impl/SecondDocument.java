package com.company.documents.impl;

import com.company.documents.AbstractDocument;
import com.company.documents.PaperSize;

import static com.company.documents.Type.ORDER;

public class SecondDocument extends AbstractDocument {
	public SecondDocument(Integer id) {
		super(id, ORDER, 3, new PaperSize(22, 20));
	}
}
