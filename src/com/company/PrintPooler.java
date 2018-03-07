package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PrintPooler implements Runnable {
	private PrinterManager manager;
	private AtomicInteger id = new AtomicInteger(0);
	private volatile boolean running = true;

	PrintPooler(PrinterManager manager) {
		this.manager = manager;
	}

	public void stop() {
		running = false;
		System.out.println("Pooler stoping");
	}

	@Override
	public void run() {
		try {
			while (running) {
				TimeUnit.SECONDS.sleep(1);
				Request request = new Request(id.getAndIncrement(), Request.Type.ORDER, 5, new PaperSize(24, 24));
				boolean print = manager.print(request);
				if (!print) {
					System.out.println("Can't send to print. Service offline.");
				} else {
					System.out.println(String.format("Success pool task #%d.", request.getId()));
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
