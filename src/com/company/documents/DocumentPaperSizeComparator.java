package com.company.documents;

import java.util.Comparator;

public class DocumentPaperSizeComparator implements Comparator<Document> {
	@Override
	public int compare(Document o1, Document o2) {
		return o1.getPaperSize().getPerimeter().compareTo(o2.getPaperSize().getPerimeter());
	}
}
