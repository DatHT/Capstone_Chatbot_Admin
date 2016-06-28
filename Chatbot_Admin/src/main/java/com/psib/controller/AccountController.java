package com.psib.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.common.DatabaseException;
import com.psib.model.Staff;
import com.psib.service.IStaffManager;

@Controller
public class AccountController {

	public static final String ERROR = "ERROR";
	@Autowired
	IStaffManager staffManager;

	@RequestMapping(value = "manageAccount")
	public String manageAccount(Model model) {
		List<Staff> listStaff = staffManager.getAllStaff();
		model.addAttribute("STAFFS", listStaff);

		return "manageAccount";
	}

	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public @ResponseBody String addNewAccount(@RequestParam("username") String username,
			@RequestParam(value = "isAdminChk") Boolean isAdmin, Model model) {
		String responseText = "";
		try {
			if (staffManager.getStaffByUsername(username) != null) {
				responseText = "Username is already exist!";
			} else {
				staffManager.createNewStaffAccount(username, null, isAdmin);
				responseText = "New account was created successfully!";
			}
		} catch (DatabaseException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";
		}
		return responseText;
	}

	@RequestMapping(value = "/profile")
	public String viewUserProfile(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		Staff staff = staffManager.getStaffByUsername(username);

		model.addAttribute("USER", staff);

		return "profile";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody String changePassword(@RequestParam("password") String password,
			@RequestParam("new_password") String newPassword, HttpServletRequest request, Model model) {
		String responseText = "";

		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		Staff staff = staffManager.getStaffByUsername(username);
		try {
			if (password.equals(staff.getPassword())) {
				staff.setPassword(newPassword);
				staffManager.updateStaff(staff);

				responseText = "Change password successfully!";
			} else {
				responseText = "Your password is not correct!";
			}

			return responseText;
		} catch (DatabaseException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";
		}

	}

	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	public @ResponseBody String updateUserInfo(@RequestParam("txtPhone") String phone,
			@RequestParam("txtEmail") String email, @RequestParam("txtAddress") String address,
			HttpServletRequest request) {
		String responseText = "";

		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		Staff staff = staffManager.getStaffByUsername(username);

		if (phone != "") {
			staff.setPhone(phone);
		}
		if (email != "") {
			staff.setEmail(email);
		}
		if (address != "") {
			staff.setAddress(address);
		}
		try {
			staffManager.updateStaff(staff);
			responseText = "Update successfully!";
		} catch (Exception e) {
			responseText = "Error occurs! Please try again!";
		}
		return responseText;
	}
}
