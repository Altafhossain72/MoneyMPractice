package com.exam.controller;
	
	import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

import com.exam.Dao.UserRepo;
import com.exam.model.User;


	@RestController
	@RequestMapping("/user")
	public class UserController {
		@Autowired
		UserRepo userRepo;
		
		@PostMapping("/add")
		public User addUser(@RequestBody User user) {
			System.out.println(user.toString());
			User uM = userRepo.saveAndFlush(user);
			
			return uM;
		}
		
		@GetMapping("/list")
		public List<User> getAll() {
			List<User> uM = userRepo.findAll();		
			return uM;
		}
		
		@GetMapping("/one/{id}")
		public User getById(@PathVariable("id") int id) {
			User uM = userRepo.findById(id).get();
			return uM;
		}
		
		@GetMapping("/delete/{id}")
		public User deleteById(@PathVariable("id") int id) {
			userRepo.deleteById(id);
			return new User();
		}
		
		@PostMapping("/login")
		public User login(@RequestBody User user) {
			User u = userRepo.getUserByUsername(user.getEmail());
			if(u != null) {
				if(u.getPassword().equals(user.getPassword())) {
					return u;
				}
			}
			return new User();
		}

	
	
	
}
