package SecurityApp.services;

import SecurityApp.models.Role;
import SecurityApp.models.User;
import SecurityApp.repositories.RoleRepository;
import SecurityApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegistrationServiceImp implements RegistrationService {

    private final UserRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RoleRepository authRepository;
    Role auth1;

    @Autowired
    public RegistrationServiceImp(
            UserRepository peopleRepository,
            PasswordEncoder passwordEncoder,
            UserDetailsService personDetailsService,
            RoleRepository authRepository, RoleService roleService) {

        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.authRepository = authRepository;

    }


    public void makeEncode(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        peopleRepository.save(user);
    }


    @Transactional
    public void registerSuperAdmin(User user) {
        System.out.println("hhhhhhhhhhhhhh");
        Role auth3 = roleService.findRole("ROLE_USER");
        Role auth4 = roleService.findRole("ROLE_ADMIN");
        auth3.setPeople(user);
        auth4.setPeople(user);

    }

//    @Transactional
//    public void registerAdmin(User user, Auth auth4) {
//
//        if (Objects.equals(auth4.getRole(), "ADMIN")) {
//            auth4 = roleService.findRole("ROLE_ADMIN");
//            user.setAuths(auth4);
//        } else {
//            auth4 = roleService.findRole("ROLE_USER");
//            user.setAuths(auth4);
//        }
//
//
//    }

    @Transactional
    public void registerAAdmin(User user, Role auth4, Boolean isAdmin, Boolean isUser) {
        if (isAdmin == false && isUser == false) {
            throw new RuntimeException("Not ok! where is Role");
        }
        if (isAdmin && isUser) {
            auth4 = roleService.findRole("ROLE_ADMIN");
            user.setAuths(auth4);
            auth4 = roleService.findRole("ROLE_USER");
            user.setAuths(auth4);
        }
        if (isAdmin) {
            auth4 = roleService.findRole("ROLE_ADMIN");
            user.setAuths(auth4);
        }
        if (isUser) {
            auth4 = roleService.findRole("ROLE_USER");
            user.setAuths(auth4);
        }


    }


}
