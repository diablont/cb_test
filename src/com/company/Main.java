package com.company;

public class Main {
	static final PrinterManager printerManager = new PrinterManager(new Printer());
	private static final PrintPooler printPooler = new PrintPooler(printerManager);
	private static final Stopper stopper = new Stopper(printerManager);

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Hook());
		printerManager.start();
		new Thread(printPooler, "Thread #" + 1).start();
		new Thread(stopper, "Thread #" + 1).start();
		while (true) {
		}
	}

	static final class Hook extends Thread {
		public void run() {
			printerManager.stop();
			printPooler.stop();
			System.out.flush();
		}
	}


}
