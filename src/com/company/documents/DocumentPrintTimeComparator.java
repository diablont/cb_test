package com.company.documents;

import java.util.Comparator;

public class DocumentPrintTimeComparator implements Comparator<Document> {
	@Override
	public int compare(Document o1, Document o2) {
		return o1.getPrintTime().compareTo(o2.getPrintTime());
	}
}
