package com.company.documents;

public interface Document {
	Integer getPrintTime();
	PaperSize getPaperSize();
	Integer getId();
	Type getType();
}
