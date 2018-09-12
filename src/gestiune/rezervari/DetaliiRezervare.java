package gestiune.rezervari;

public class DetaliiRezervare {
	
	public String numarOrdine = null;
	public String masa = null;
	public String biliard = null;
	public String bowling = null;
	public String darts = null;
	
	public String getBiliard() {
		return biliard;
	}

	public String getNumarOrdine() {
		return numarOrdine;
	}
	
	public void setNumarOrdine(String numarOrdine) {
		this.numarOrdine = numarOrdine;
	}

	public String getMasa() {
		return masa;
	}

	public String getBowling() {
		return bowling;
	}

	public String getDarts() {
		return darts;
	}

	public DetaliiRezervare(String masa, String biliard, String bowling, String darts) {
		this.masa = masa;
		this.biliard = biliard;
		this.bowling = bowling;
		this.darts = darts;
	}

}
