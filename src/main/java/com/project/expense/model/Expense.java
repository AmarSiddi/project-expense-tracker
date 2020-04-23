package com.project.expense.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant expenseDate;

    @NotBlank
    @Size(min = 3, max = 100)
    private String description;

    private String location;

    //@ManyToOne(targetEntity = Category.class, cascade = CascadeType.ALL)
    //@JoinColumn(name="category_id", referencedColumnName = "id")
    @ManyToOne()
    private Category category;


    //@ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    //@JoinColumn(name="user_id", referencedColumnName = "id")
    @ManyToOne()
    private User user;

}

