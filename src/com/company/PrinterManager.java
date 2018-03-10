package com.company;

import com.company.documents.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class PrinterManager {
	private final Printer printer;
	private final Queue<Document> documentsPool;
	private final List<Document> printedDocsList;
	private final RequestTypeComparator typeComparator = new RequestTypeComparator();
	private final RequestPrintTimeComparator printTimeComparator = new RequestPrintTimeComparator();
	private final RequestPaperSizeComparator paperSizeComparator = new RequestPaperSizeComparator();
	private volatile boolean serviceRunning = true;

	PrinterManager(Printer printer) {
		this.printer = printer;
		this.documentsPool = new ConcurrentLinkedQueue<>();
		this.printedDocsList = new CopyOnWriteArrayList<>();
	}

	public void start() {
		ExecutorService exec_service = Executors.newSingleThreadExecutor();
		exec_service.submit(new PrintQueue());
		exec_service.shutdown();
	}

	public boolean print(final Document r) {
		return serviceRunning && documentsPool.offer(r);
	}

	public boolean cancelTask(final Document r) {
		return serviceRunning && documentsPool.remove(r);
	}

	public List<Document> getDocumentsSortedByPrintOrder() {
		return printedDocsList;
	}

	public List<Document> getDocumentsSortedByType() {
		return getSortedListOfPrintedDocs(typeComparator);
	}

	public List<Document> getDocumentsSortedByPrintTime() {
		return getSortedListOfPrintedDocs(printTimeComparator);
	}

	public List<Document> getDocumentsSortedByPaperSize() {
		return getSortedListOfPrintedDocs(paperSizeComparator);
	}

	public Double getAveragePrintTime() {
		Integer fullTime = 0;
		Integer count = 0;
		if (printedDocsList.isEmpty()) {
			return fullTime.doubleValue();
		}
		for (Document next : printedDocsList) {
			fullTime += next.getPrintTime();
			count++;
		}
		return fullTime.doubleValue() / count;
	}

	private List<Document> getSortedListOfPrintedDocs(Comparator<Document> comparator) {
		List<Document> sortedList = new ArrayList<>(printedDocsList);
		Collections.sort(sortedList, comparator);
		return sortedList;
	}

	public List<Document> stop() {
		serviceRunning = false;
		System.out.println("Manager stopping");
		return new ArrayList<>(documentsPool);
	}

	private class PrintQueue implements Runnable {
		@Override
		public void run() {
			while (serviceRunning) {
				printOne();
			}
			System.out.println("Manager stopped");
		}

		private void printOne() {
			final Document documentToPrint = documentsPool.poll();
			if (documentToPrint == null) {
				return;
			}
			printedDocsList.add(documentToPrint);
			printer.print(documentToPrint);
		}
	}
}