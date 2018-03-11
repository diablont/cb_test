package com.company.documents;

import java.util.Comparator;

public class DocumentTypeComparator implements Comparator<Document> {
	@Override
	public int compare(Document o1, Document o2) {
		return o1.getType().compareTo(o2.getType());
	}
}
