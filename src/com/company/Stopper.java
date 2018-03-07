package com.company;

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
			List<Request> requests = manager.stop();
			for (Request request : requests) {
				System.out.println(String.format("Not printed #%d", request.getId()));
			}
			List<Request> responsesSortedByPrintOrder = manager.getResponsesSortedByPrintOrder();
			for (Request request : responsesSortedByPrintOrder) {
				System.out.println("Printed: " + request.getId());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
