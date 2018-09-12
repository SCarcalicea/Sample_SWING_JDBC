package gestiune.divertisment;

public class DetaliiDivertisment {
	
	public String numarOrdine = null;
	public String biliard = null;
	public String bowling = null;
	public String darts = null;
	
	public DetaliiDivertisment(String biliard, String darts, String bowling) {
		this.biliard = biliard;
		this.bowling = bowling;
		this.darts = darts;
	}
	
	public String getBiliard() {
		return biliard;
	}

	public String getNumarOrdine() {
		return numarOrdine;
	}
	
	public void setNumarOrdine(String numarOrdine) {
		this.numarOrdine = numarOrdine;
	}

	public String getBowling() {
		return bowling;
	}

	public String getDarts() {
		return darts;
	}

}
