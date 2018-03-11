package com.company.documents;

import java.util.Objects;

public class PaperSize {
	private final Integer width;
	private final Integer height;
	private final Integer perimeter;

	public PaperSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.perimeter = width * height;
	}

	public Integer getPerimeter() {
		return perimeter;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PaperSize paperSize = (PaperSize) o;
		return Objects.equals(width, paperSize.width) &&
				Objects.equals(height, paperSize.height);
	}

	@Override
	public int hashCode() {
		return Objects.hash(width, height);
	}

	@Override
	public String toString() {
		return "PaperSize{" +
				"width=" + width +
				", height=" + height +
				'}';
	}
}
