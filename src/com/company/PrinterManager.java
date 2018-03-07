package com.company;

import java.util.*;
import java.util.concurrent.*;

final class PrinterManager {
	private final Queue<Request> requests;
	private final List<Request> printed;
	private final Request.RequestTypeComparator typeComparator = new Request.RequestTypeComparator();
	private final Request.RequestPrintTimeComparator printTimeComparator = new Request.RequestPrintTimeComparator();
	private final Request.RequestPaperSizeComparator paperSizeComparator = new Request.RequestPaperSizeComparator();
	private volatile boolean running = true;
	private ExecutorService exec_service = Executors.newSingleThreadExecutor();

	PrinterManager() {
		this.requests = new ConcurrentLinkedQueue<>();
		this.printed = new CopyOnWriteArrayList<>();
	}

	public void start() {
		exec_service.submit(new PrintQueue());
		exec_service.shutdown();
	}

	public boolean print(final Request r) {
		return running && requests.offer(r);
	}

	public boolean cancelTask(final Request r) {
		return requests.remove(r);
	}

	public List<Request> getResponsesSortedByPrintOrder() {
		//Т.к. печать идет в один поток, то нет смысла сортировать.
		return printed;
	}

	public List<Request> getResponsesSortedByType() {
		return getSortedListOfPrintedDocs(typeComparator);
	}

	public List<Request> getResponsesSortedByPrintTime() {
		return getSortedListOfPrintedDocs(printTimeComparator);
	}

	public List<Request> getResponsesSortedByPaperSize() {
		return getSortedListOfPrintedDocs(paperSizeComparator);
	}

	public Double getAveragePrintTime() {
		Integer fullTime = 0;
		Integer count = 0;
		if (printed.isEmpty()) {
			return fullTime.doubleValue();
		}
		for (Request next : printed) {
			fullTime += next.getPrintTime();
			count++;
		}
		return fullTime.doubleValue() / count;
	}

	private List<Request> getSortedListOfPrintedDocs(Comparator<Request> comparator) {
		List<Request> sortedList = new ArrayList<>(printed);
		Collections.sort(sortedList, comparator);
		return sortedList;
	}

	public List<Request> stop() {
		running = false;
		System.out.println("Manager stopping");
		return new ArrayList<>(requests);
	}

	private class PrintQueue implements Runnable {
		@Override
		public void run() {
			final Thread currentThread = Thread.currentThread();
			final String oldName = currentThread.getName();
			currentThread.setName("Print-Thread");
			try {
				while (running) {
					printOne();
				}
			} finally {
				currentThread.setName(oldName);
			}
			System.out.println("Manager stopped");
		}

		private void printOne() {
			final Request request = requests.poll();
			if (request == null) {
				return;
			}
			final String msg = String.format("message #%d", request.getId());
			printed.add(request);
			System.out.println("Start. " + msg);
			try {
				TimeUnit.SECONDS.sleep(request.getPrintTime());
			} catch (InterruptedException e1) {
				System.out.println("Interrupted");
			}
			System.out.println("Stop. " + msg);
		}
	}
}