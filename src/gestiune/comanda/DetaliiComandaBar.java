package gestiune.comanda;

public class DetaliiComandaBar {
	private String categorie = null;
	private String denumire = null;
	private String cantitate = null;
	private String pret = null;
	
	public DetaliiComandaBar(String cat, String den, String cant, String pret) {
		this.categorie = cat;
		this.denumire = den;
		this.cantitate = cant;
		this.pret = pret;
	}
	
	public String getCategorie() {
		return categorie;
	}
	public String getDenumire() {
		return denumire;
	}
	public String getCantitate() {
		return cantitate;
	}
	
	public void setCantitate(String cant) {
		this.cantitate = cant;
	}
	
	public String getPret() {
		return pret;
	}
	
	public String toString() {
		return "Cantitate=" + cantitate + ";Denumire=" + denumire + ";Categorie=" + categorie + ";Pret=" + pret;
	}

}
