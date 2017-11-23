package ua.nure.kn156.legenka;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	public User(){}
	public User(User user) {
		id=user.getId();
		firstName=user.getFirstName();
		lastName=user.getLastName();
		dateOfBirth=user.getDateOfBirth();
	}
	public User(String firstName, String lastName, Date date){
		this.firstName=firstName;
		this.lastName=lastName;
		this.dateOfBirth=date;
	}
	public User (Long id, String firstName, String lastName, Date date){
		this.id=id;
		this.firstName=firstName;
		this.lastName=lastName;
		this.dateOfBirth=date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Object getFullName() {
        return getLastName() + ", " + getFirstName();
    }
//	public String getFullName() {
//		if(getFirstName() == null || getLastName() == null){
//			throw new IllegalArgumentException("First name or last name is empty");
//	}
//		return getLastName()+", "+ getFirstName();
//	}
	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);
		int currentDay = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(dateOfBirth);
		int year = calendar.get(Calendar.YEAR);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		int age = currentYear - year -1;
		if(currentDay>=day)
			age++;
		return age;
	}
	
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}
		if(this==obj){
			return true;
		}
		if(this.getId()==null  && ((User)obj).getId()==null){
			return true;
		}
		return this.getId().equals(((User)obj).getId());
	}
public int hashCode(){
	if(this.getId()==null){
		return 0;
	}
	return this.getId().hashCode();
}
}
