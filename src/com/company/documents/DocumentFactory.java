package com.company.documents;

import java.util.concurrent.ThreadLocalRandom;

import static com.company.documents.Type.*;

public class DocumentFactory {
	public DocumentFactory() {
	}

	public Document createFirstDocument(Integer id) {
		return new Document(id, BILL, 2, new PaperSize(24, 26));
	}

	public Document createSecondDocument(Integer id) {
		return new Document(id, ORDER, 3, new PaperSize(22, 20));
	}

	public Document createThirdDocument(Integer id) {
		return new Document(id, RECORDS, 4, new PaperSize(10, 10));
	}

	public Document createRandomDocument(Integer id) {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 4);
		switch (randomNum) {
			case 1:
				return createFirstDocument(id);
			case 2:
				return createSecondDocument(id);
			case 3:
				return createThirdDocument(id);
			default:
				return createFirstDocument(id);
		}
	}
}
