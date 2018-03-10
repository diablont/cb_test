package com.company;

import com.company.documents.Document;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Stopper implements Runnable {
	private final PrinterManager manager;

	Stopper(final PrinterManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(12);
			List<Document> requests = manager.stop();
			for (Document document : requests) {
				System.out.println(String.format("Not printed #%d", document.getId()));
			}
			List<Document> responsesSortedByPrintOrder = manager.getDocumentsSortedByPrintOrder();
			System.out.println("SortedByPrintOrder");
			for (Document document : responsesSortedByPrintOrder) {
				System.out.println("Printed: " + document.getId());
			}
			List<Document> responsesSortedByPaperSize = manager.getDocumentsSortedByPaperSize();
			System.out.println("SortedByPaperSize");
			for(Document document : responsesSortedByPaperSize) {
				System.out.println(document);
			}
			List<Document> documentsSortedByPrintTime = manager.getDocumentsSortedByPrintTime();
			System.out.println("SortedByPrintTime");
			for(Document document : documentsSortedByPrintTime) {
				System.out.println(document);
			}
			List<Document> documentsSortedByType = manager.getDocumentsSortedByType();
			System.out.println("SortedByByType");
			for(Document document : documentsSortedByType) {
				System.out.println(document);
			}
			Double averagePrintTime = manager.getAveragePrintTime();
			System.out.println("averagePrintTime: "+averagePrintTime);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
