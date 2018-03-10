package com.company;

import com.company.documents.Document;

import java.util.concurrent.TimeUnit;

public class Printer {

	public void print(Document document) {
		final String msg = String.format("message #%d", document.getId());
		System.out.println("Start. " + msg);
		try {
			TimeUnit.SECONDS.sleep(document.getPrintTime());
		} catch (InterruptedException e1) {
			System.out.println("Interrupted");
		}
		System.out.println("Stop. " + msg);
	}
}
