package com.company;

import com.company.documents.Document;
import com.company.documents.DocumentPaperSizeComparator;
import com.company.documents.DocumentPrintTimeComparator;
import com.company.documents.DocumentTypeComparator;

import java.util.*;
import java.util.concurrent.*;

final class PrinterManager {
	private final Printer printer;
	private final Queue<Document> documentsPool = new ConcurrentLinkedQueue<>();
	private final List<Document> printedDocsList = new ArrayList<>();
	private final DocumentTypeComparator typeComparator = new DocumentTypeComparator();
	private final DocumentPrintTimeComparator printTimeComparator = new DocumentPrintTimeComparator();
	private final DocumentPaperSizeComparator paperSizeComparator = new DocumentPaperSizeComparator();
	private volatile boolean serviceRunning = true;
	private volatile boolean printing = false;

	public PrinterManager(Printer printer) {
		this.printer = printer;
		ExecutorService exec_service = Executors.newSingleThreadExecutor();
		exec_service.submit(new PrintQueue());
		exec_service.shutdown();
	}

	public List<Document> stop() {
		serviceRunning = false;
		System.out.println("Manager stopping");
		while (printing) {
			waitPrintingEnd();
		}
		ArrayList<Document> documents = new ArrayList<>(documentsPool);
		documentsPool.clear();
		return documents;
	}

	private void waitPrintingEnd() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			System.out.println("Interrupt!");
		}
	}

	public boolean addTask(final Document document) {
		return serviceRunning && documentsPool.offer(document);
	}

	public boolean cancelTask(final Document document) {
		return serviceRunning && documentsPool.remove(document);
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

	private class PrintQueue implements Runnable {
		@Override
		public void run() {
			try {
				while (serviceRunning) {
					printOne();
				}
			} finally {
				printing = false;
			}
			System.out.println("Manager stopped");
		}

		private void printOne() {
			printing = true;
			final Document documentToPrint = documentsPool.poll();
			if (documentToPrint == null) {
				return;
			}
			printer.print(documentToPrint);
			printedDocsList.add(documentToPrint);
			printing = false;
		}
	}
}