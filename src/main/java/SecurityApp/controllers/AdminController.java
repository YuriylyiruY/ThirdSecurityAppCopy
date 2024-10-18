package SecurityApp.controllers;

import SecurityApp.models.Role;
import SecurityApp.models.User;
import SecurityApp.security.UserDetails;
import SecurityApp.services.UserService;
import SecurityApp.services.UserDetailsService;
import SecurityApp.services.RegistrationService;
import SecurityApp.services.RoleService;
import SecurityApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/")
public class AdminController {

    private final UserService peopleService;
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService peopleService, PersonValidator personValidator, RegistrationService registrationService, UserDetailsService personDetailsService, RoleService roleService) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.roleService = roleService;

    }

    @GetMapping("/adminPage")
    public String index(Model model, @ModelAttribute("ttt") Role auth, @ModelAttribute("userS") User user) {
        model.addAttribute("people", peopleService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();


        model.addAttribute("user", personDetails.getPerson());
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);

        return "adminPage";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") User user,
                            @ModelAttribute("ttt") Role auth,
                            Model model) {
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);


        return "new";
    }


    @PostMapping("/")
    public String create(@ModelAttribute("person") @Valid User user,
                         // @ModelAttribute("ttt") Auth auth,
                         Role auth1,
                         BindingResult bindingResult) {

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "new";
        registrationService.makeEncode(user);
        roleService.addRolesToTable(user);
        //System.out.println(auth.getRole());
        //System.out.println(auth.getIdForAuth());
        //user.setAuths(auth);
        // registrationService.registerAdmin(user, auth);
        registrationService.registerAAdmin(user, auth1, user.getAdmin(), user.getUser());
        peopleService.save(user);
        return "redirect:/adminPage";
    }

    @PostMapping("/a")
    public String createSuperAdmin(@ModelAttribute("persona") @Valid User user,
                                   BindingResult bindingResult) {

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "admin";

        registrationService.registerSuperAdmin(user);

        peopleService.save(user);
        return "redirect:/userPage";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id, @ModelAttribute("ttt") Role auth) {
        model.addAttribute("person", peopleService.findOne(id));

        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        return "edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid User user, BindingResult bindingResult,
                         Role auth,
                         HttpServletRequest request) {
        if (bindingResult.hasErrors())
            return "edit";
        int id = Integer.parseInt(request.getParameter("id"));
        registrationService.makeEncode(user);
        registrationService.registerAAdmin(user, auth, user.getAdmin(), user.getUser());
        peopleService.update(id, user);
        return "redirect:/adminPage";
    }
//    @PostMapping("/insert")
//    public String insertExample(Model model, Auth auth) {
//        model.addAttribute("example", auth);
//        return "example-form";
//    }

    @PostMapping("/del/{id}")
    public String delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));

        peopleService.delete(id);
        return "redirect:/adminPage ";
    }

    @GetMapping("/adminUserPage")
    public String userAdminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        model.addAttribute("person", personDetails.getPerson());
        return "adminUserPage";
    }
}





