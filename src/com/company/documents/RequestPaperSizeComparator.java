package com.company.documents;

import java.util.Comparator;

public class RequestPaperSizeComparator implements Comparator<Document> {
	@Override
	public int compare(Document o1, Document o2) {
		return o1.getPaperSize().compareTo(o2.getPaperSize());
	}
}
