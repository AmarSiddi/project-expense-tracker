package com.project.expense.repo;


import com.project.expense.model.Category;
import com.project.expense.model.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    @Query("SELECT new com.project.expense.repo.ExpenseResponse(e.id, e.description , e.location, e.expenseDate, c.id, c.name) FROM Expense e JOIN e.user u JOIN e.category c WHERE u.id=?1 ")
    List<ExpenseResponse> getJointInformation(Long id);

}
