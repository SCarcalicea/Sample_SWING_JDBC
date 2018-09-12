package gestiune.detalii;

import java.time.LocalDate;

public class UserData {
	private String userName;
	@SuppressWarnings("unused")
	private String password;
	private LocalDate hireDate;
	private String position;
	
	public UserData(String user, String pass, LocalDate hire, String pos) {
		this.userName = user;
		this.password = pass;
		this.hireDate = hire;
		this.position = pos;
	}
	
	public String toString() {
		String userText = "Nume Utilizator -> " + this.userName + ";" + System.lineSeparator();
		String hireDate = "Data Angajare   -> " + this.hireDate.toString() + ";" + System.lineSeparator();
		String position = "Functie ocupata -> " + this.position + ";" + System.lineSeparator();
		
		return userText + hireDate + position;
	}
	
}
