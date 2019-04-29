package org.java.core.base.map.sortedmap;

import java.time.LocalDate;

public class PersonalDetails {

	String occupation;
	LocalDate dataOfBirth;
	String city;

	public PersonalDetails(String occupation, LocalDate dataOfBirth, String city) {
		this.occupation = occupation;
		this.dataOfBirth = dataOfBirth;
		this.city = city;
	}

	@Override
	public String toString() {
		return this.occupation + ", from " + this.city;
	}
}
