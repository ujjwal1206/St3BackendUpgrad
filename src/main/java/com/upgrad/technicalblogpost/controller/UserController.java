package com.upgrad.technicalblogpost.controller;
import com.upgrad.technicalblogpost.model.User;
import com.upgrad.technicalblogpost.model.UserProfile;
import com.upgrad.technicalblogpost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    public UserController(){
        System.out.println("*********** UserController ***********");
    }
    // URL : users/login
    @Autowired
    private UserService userService;

    @RequestMapping("users/login") //localhost:8080/users/login : GET
    public String login(Model model){
        model.addAttribute("user", new User());
        return "users/login";
    }


    @RequestMapping(value="users/login", method= RequestMethod.POST)  // localhost:8080/users/login : POST
    public String loginUser(User user, HttpSession session){
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        User existingUser = userService.login(user);
        if(existingUser != null){
            System.out.println("You are Authenticated");
            session.setAttribute("loggeduser", existingUser);
            return "redirect:/posts"; //localhost:8080/posts : GET
        }else{
            System.out.println("You are NOT Authenticated, Get lost");
            return "users/login"; //localhost:8080/users/login : GET
        }
    }


    @RequestMapping("users/registration")
    public String registration(Model model){
        User user = new User();
        UserProfile profile= new UserProfile();
        user.setProfile(profile);
        model.addAttribute("User", user);
        return "users/registration";
    }
    @RequestMapping(value="users/registration", method= RequestMethod.POST)
    public String registerUser(User user){
        userService.registerUser(user);
        return "users/login";
    }


}
