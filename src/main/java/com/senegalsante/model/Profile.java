package com.senegalsante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String birthDate;
    private String gender;
    private String bloodGroup;
    private String allergies;
    private boolean isMain;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Profile() {}

    public Profile(Long id, String firstName, String lastName, String birthDate, String gender, String bloodGroup, String allergies, boolean isMain, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.isMain = isMain;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public boolean isMain() { return isMain; }
    public void setMain(boolean main) { isMain = main; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
