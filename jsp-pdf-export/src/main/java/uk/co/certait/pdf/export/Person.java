package uk.co.certait.pdf.export;

public class Person {

	private int id;

	private String forename;

	private String surname;

	public Person(int id, String forename, String surname) {
		this.id = id;
		this.forename = forename;
		this.surname = surname;
	}

	public int getId() {
		return id;
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}
}
