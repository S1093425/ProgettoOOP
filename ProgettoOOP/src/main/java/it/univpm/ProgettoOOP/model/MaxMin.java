package it.univpm.ProgettoOOP.model;

public class MaxMin {
	
	private String maxS;
	private int max;
	private String minS;
	private int min;
	public MaxMin() {
	}
	public MaxMin(String maxS, int max, String minS, int min) {
		this.maxS = maxS;
		this.max = max;
		this.minS = minS;
		this.min = min;
	}
	public String getMaxS() {
		return maxS;
	}
	public void setMaxS(String maxS) {
		this.maxS = maxS;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public String getMinS() {
		return minS;
	}
	public void setMinS(String minS) {
		this.minS = minS;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
}
