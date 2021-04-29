package xyz.mlhmz.rapidTestApp.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public Person(Long id, String firstname, String lastname, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstname;
        this.lastName = lastname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Person(String firstname, String lastname, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstname;
        this.lastName = lastname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public Person() {}

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return firstName + " " + lastName + ", " + address;
    }
}

