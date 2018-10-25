package org.java.core.base.collection.sortedMap;

import java.time.LocalDate;

class PersonalDetails {
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
