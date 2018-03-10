package com.company.documents;

import com.company.documents.impl.FirstDocument;
import com.company.documents.impl.SecondDocument;
import com.company.documents.impl.ThirdDocument;

import java.util.concurrent.ThreadLocalRandom;

public class DocumentFactory {
	public DocumentFactory() {}

	public Document createFirstDocument(Integer id) {
		return new FirstDocument(id);
	}

	public Document createSecondDocument(Integer id) {
		return new SecondDocument(id);
	}

	public Document createThirdDocument(Integer id) {
		return new ThirdDocument(id);
	}

	public Document createRandomDocument(Integer id) {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 4);
		switch (randomNum) {
			case 1 : return createFirstDocument(id);
			case 2 : return createSecondDocument(id);
			case 3 : return createThirdDocument(id);
			default : return createFirstDocument(id);
		}
	}
}
