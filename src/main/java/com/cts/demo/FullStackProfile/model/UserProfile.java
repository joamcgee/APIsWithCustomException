package com.cts.demo.FullStackProfile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String employeeId;

    @Column
    @Size(min = 2, message = "Name should have at least 2 characters")
    public String name;

    @Column
    public String department;

    @Column
    @Past(message = "Joining Date should be in the past")
    public LocalDate dateJoined;

    @Column
    public String salary;

}
