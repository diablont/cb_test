package com.company;

import com.company.documents.AbstractDocument;
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
			for (Document request : requests) {
				System.out.println(String.format("Not printed #%d", request.getId()));
			}
			List<Document> responsesSortedByPrintOrder = manager.getResponsesSortedByPrintOrder();
			for (Document request : responsesSortedByPrintOrder) {
				System.out.println("Printed: " + request.getId());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
