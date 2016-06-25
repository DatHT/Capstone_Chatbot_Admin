package com.psib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.psib.model.Staff;
import com.psib.service.IStaffManager;

@Controller
public class AccountController {

	@Autowired
	IStaffManager staffManager;

	@RequestMapping(value = "manageAccount")
	public String manageAccount(Model model) {
		List<Staff> listStaff = staffManager.getAllStaff();
		model.addAttribute("STAFFS", listStaff);
		
		return "manageAccount";
	}

	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public String addNewAccount(@RequestParam("username") String username, @RequestParam("email") String email,
			@RequestParam(value = "isAdminChk", required = false) String isAdmin, Model model) {
		staffManager.createNewStaffAccount(username, email, (isAdmin != null));
		return "redirect:manageAccount";
	}

	@RequestMapping(value = "/checkIsExist")
	public boolean checkIsExist(@RequestParam("username") String username) {
		if (staffManager.getStaffByUsername(username) != null) {
			return true;
		}
		return false;
	}
}
