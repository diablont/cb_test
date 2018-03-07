package com.company;

import java.util.Comparator;
import java.util.Objects;

public class Request {
	private final Integer id;
	private final Integer printTime;
	private final Type type;
	private final PaperSize paperSize;

	Request(final Integer id, final Type type, final Integer printTime, final PaperSize paperSize) {
		this.id = id;
		this.type = type;
		this.printTime = printTime;
		this.paperSize = paperSize;
	}

	public Integer getId() {
		return id;
	}

	public Integer getPrintTime() {
		return printTime;
	}

	public Type getType() {
		return type;
	}

	public PaperSize getPaperSize() {
		return paperSize;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Request request = (Request) o;
		return id.equals(request.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public enum Type {
		ORDER("Purchase order"), BILL("Payment bill"), RECORDS("Account statement");
		private final String name;

		Type(String s) {
			name = s;
		}

		public String toString() {
			return this.name;
		}
	}

	public static class RequestTypeComparator implements Comparator<Request> {
		@Override
		public int compare(Request o1, Request o2) {
			return o1.getType().compareTo(o2.getType());
		}
	}

	public static class RequestPrintTimeComparator implements Comparator<Request> {
		@Override
		public int compare(Request o1, Request o2) {
			return o1.getPrintTime().compareTo(o2.getPrintTime());
		}
	}

	public static class RequestPaperSizeComparator implements Comparator<Request> {
		@Override
		public int compare(Request o1, Request o2) {
			return o1.getPaperSize().compareTo(o2.getPaperSize());
		}
	}
}
