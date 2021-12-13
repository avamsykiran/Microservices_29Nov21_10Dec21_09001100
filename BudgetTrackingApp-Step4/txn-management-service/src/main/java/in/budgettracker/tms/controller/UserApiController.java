package in.budgettracker.tms.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.budgettracker.tms.entity.TransactionEntity;
import in.budgettracker.tms.exception.TxnManagementException;
import in.budgettracker.tms.model.UserModel;
import in.budgettracker.tms.service.TransactionService;
import in.budgettracker.tms.service.UserService;

@RestController
@RequestMapping("/users")
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService txnService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserModel> getById(@PathVariable("userId") Long userId) throws TxnManagementException {
		UserModel model = userService.getUserById(userId);
		return model==null? new ResponseEntity<>(HttpStatus.NOT_FOUND):ResponseEntity.ok(model);
	}
	
	@GetMapping("/{userId}/txns")
	public ResponseEntity<List<TransactionEntity>> getAllTxns(@PathVariable("userId") Long userId) throws TxnManagementException {
		return new ResponseEntity<List<TransactionEntity>>(txnService.getAllByUserId(userId), HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/txns/{start}/{end}")
	public ResponseEntity<List<TransactionEntity>> getAllTxnsInDates(
			@PathVariable("userId") Long userId,
			@PathVariable("start") @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime start,
			@PathVariable("end") @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime end) throws TxnManagementException {
		return new ResponseEntity<List<TransactionEntity>>(txnService.getAllByUserId(userId,start,end), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) throws TxnManagementException {
		user = userService.createUser(user);
		return new ResponseEntity<UserModel>(user, HttpStatus.CREATED);
	}
}
