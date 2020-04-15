package com.project.expense.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="expense")
public class Expense {

    @Id
    private int id;

    private Instant expenseDate;
    private String description;

    private String location;
    @ManyToOne
    private Category category;

    @JsonIgnore
    @ManyToOne
    private User user;

}

