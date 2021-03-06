package com.company.documents;

import java.util.Objects;

public class Document {
	private final Integer id;
	private final Integer printTime;
	private final Type type;
	private final PaperSize paperSize;

	public Document(final Integer id, final Type type, final Integer printTime, final PaperSize paperSize) {
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
		Document request = (Document) o;
		return id.equals(request.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Document{" +
				"id=" + id +
				", printTime=" + printTime +
				", type=" + type +
				", paperSize=" + paperSize +
				'}';
	}
}
