package com.codingdojo.loginreg.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.loginreg.models.User;
import com.codingdojo.loginreg.services.UserService;
import com.codingdojo.loginreg.validator.UserValidator;

@Controller
public class Users {

	private UserService us;
	
	private UserValidator uv;
	
	public Users(UserService us,
			UserValidator uv) {
		this.us = us;
		this.uv = uv;
	}
	
//	@RequestMapping("/")
//	public String index() {
//		return "index.jsp";
//	}
	
	@RequestMapping("/register")
	public String registerForm(@Valid @ModelAttribute("user") User user) {
		return "register.jsp";
	}
	
	@PostMapping("/register")
	public String registration(@Valid @ModelAttribute("user") User user,
			BindingResult result,
			Model model,
			HttpSession sesh) {
		uv.validate(user, result);
		if (result.hasErrors()) {
			return "index.jsp";
		}
		if (us.findRoleByName("ROLE_ADMIN").getUsers().size() == 0) {
			us.saveUserWithAdminRole(user);
		} else {
			us.saveWithUserRole(user);
		}
		
		return "redirect:/login";
	}
	
	@RequestMapping("/")
	public String login(@Valid @ModelAttribute("user") User user,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid credentials, please try again.");
		} if (logout != null) {
			model.addAttribute("logoutMessage", "Logout successsful");
		}
		return "index.jsp";
	}
	
	@RequestMapping("/dashboard")
	public String show(Principal principal,
			Model model) {
		User user = us.findUserByUsername(principal.getName());
		if (user.getRoles().contains(us.findRoleByName("ROLE_ADMIN"))) {
			return "redirect:/admin";
		}
		model.addAttribute("currentUser", user);
		return "show.jsp";
	}
	
	@RequestMapping("/admin")
	public String adminDashboard(Principal principal,
			@ModelAttribute("errors") String errors,
			Model model) {
		String username = principal.getName();
		model.addAttribute("currentUser", us.findUserByUsername(username));
		model.addAttribute("admin", us.findRoleByName("ROLE_ADMIN"));
		model.addAttribute("users", us.findAllUsers());
		return "admin.jsp";
	}
	
	@RequestMapping("/admin/user/delete/{id}")
	public String destroyUser(@PathVariable("id") Long id,
			RedirectAttributes errors) {
		if (us.findUserById(id).getRoles().contains(us.findRoleByName("ROLE_ADMIN"))) {
			errors.addFlashAttribute("errors", "Cannot delete another admin.");
			return "redirect:/admin";
		}
		us.deleteUserById(id);
		return "redirect:/admin";
	}
	
	@RequestMapping("/admin/user/makeAdmin/{id}")
	public String makeUserAdmin(@PathVariable("id") Long id) {
		us.saveUserWithAdminRole(us.findUserById(id));
		return "redirect:/admin";
	}
}
