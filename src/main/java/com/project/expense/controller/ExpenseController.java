package com.project.expense.controller;

import com.project.expense.model.Expense;
import com.project.expense.model.User;
import com.project.expense.repo.ExpenseRepository;
import com.project.expense.repo.ExpenseResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpense(@RequestHeader("Authorization") String token) {
        //System.out.println("user id received from token is : "+getUserIdFromJWT(token.substring(7)));
        Long user_id = getUserIdFromJWT(token.substring(7));
        User user = new User(user_id);
        return expenseRepository.getJointInformation(user_id);
    }

    //only admin can access this api
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allexpenses")
    List<Expense> getAllExpensesBy() {
        return expenseRepository.findAll();
    }

    @DeleteMapping("/expenses/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/expenses")
    ResponseEntity<Expense> createExpense(@RequestHeader("Authorization") String token, @Valid @RequestBody Expense expense) throws URISyntaxException {

        System.out.println("user id received from token is : " + getUserIdFromJWT(token.substring(7)));
        Long user_id = getUserIdFromJWT(token.substring(7));
        User user = new User(user_id);
        expense.setUser(user);
        Expense result = expenseRepository.save(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}

