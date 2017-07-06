package com.rng.site.web.controllers;

import com.rng.entities.User;
import com.rng.site.service.UserService;
import com.rng.site.web.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;



/**
 * Created by MoldovanM2 on 7/7/2017.
 */
@Controller
public class UserController extends SiteBaseController{

    @Autowired
    private UserService userService;
    @Autowired private UserValidator userValidator;
    @Autowired protected PasswordEncoder passwordEncoder;

    @Override
    protected String getHeaderTitle()
    {
        return "Login/Register";
    }

    @RequestMapping(value="/register", method= RequestMethod.GET)
    protected String registerForm(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    protected String register(@Valid @ModelAttribute("user") User user, BindingResult result,
                              Model model, RedirectAttributes redirectAttributes)
    {
        userValidator.validate(user, result);
        if(result.hasErrors()){
            return "register";
        }
        String password = user.getPassword();
        String encodedPwd = passwordEncoder.encode(password);
        user.setPassword(encodedPwd);

        User persistedCustomer = userService.createCustomer(user);
        logger.debug("Created new user with id : {} and email : {}", persistedCustomer.getId(), persistedCustomer.getEmail());
        redirectAttributes.addFlashAttribute("info", "User created successfully");
        return "redirect:/login";
    }


    @RequestMapping(value="/myAccount", method=RequestMethod.GET)
    protected String myAccount(Model model)
    {
        String email = getCurrentUser().getCustomer().getEmail();
        User user = userService.getCustomerByEmail(email);
        model.addAttribute("user", user);
        return "myAccount";
    }
}
