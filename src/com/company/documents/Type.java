package com.company.documents;

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