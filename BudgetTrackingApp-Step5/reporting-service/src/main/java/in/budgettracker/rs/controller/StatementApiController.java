package in.budgettracker.rs.controller;

import java.time.Month;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.budgettracker.rs.model.MonthlyStatement;
import in.budgettracker.rs.model.service.StatementService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/reports")
public class StatementApiController {

	@Autowired
	private StatementService stmtService;
	
	@GetMapping("/{userId}/{month}/{year}")
	@CircuitBreaker(name="getMonthlyStatement",fallbackMethod = "getMonthlyStatementFallback")
	public ResponseEntity<MonthlyStatement> getMonthlyStatement(
			@PathVariable("userId") long userId,
			@PathVariable("month") Month month,
			@PathVariable("year") int year
			){
		return new ResponseEntity<MonthlyStatement>(
				stmtService.getMonthlyStatement(userId, month, year), HttpStatus.OK);
	}
	
	public ResponseEntity<MonthlyStatement> getMonthlyStatementFallback(
			@PathVariable("userId") long userId,
			@PathVariable("month") Month month,
			@PathVariable("year") int year,
			Throwable exp
			){
		//log the 'exp'
		return new ResponseEntity<MonthlyStatement>(
				new MonthlyStatement(userId, month, year, 0, 0, 0,new TreeSet<>()), HttpStatus.OK);
	}
}
