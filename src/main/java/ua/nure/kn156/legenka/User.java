package ua.nure.kn156.legenka;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String FirstName;
	private String LastName;
	private Date dateOfBirth;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getFullName() {
		if(getFirstName() == null || getLastName() == null){
			throw new IllegalArgumentException("First name or last name is empty");
	}
		return getLastName()+", "+ getFirstName();
	}
	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);
		int currentDay = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(dateOfBirth);
		int year = calendar.get(Calendar.YEAR);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		int years = currentYear - year -1;
		if(currentDay>day)
			years++;
		return years;
	}

}
