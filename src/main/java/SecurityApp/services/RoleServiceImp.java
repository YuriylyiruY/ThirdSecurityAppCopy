package SecurityApp.services;

import SecurityApp.models.Role;
import SecurityApp.models.User;
import SecurityApp.repositories.RoleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class RoleServiceImp implements RoleService {

    private final RoleRepository authRepository;

    public RoleServiceImp(RoleRepository authRepository) {
        this.authRepository = authRepository;
    }


    public void addRolesToTable(User user) {
        Role auth = new Role("ROLE_USER");
        Role auth2 = new Role("ROLE_ADMIN");

        if (findByRole("ROLE_USER")) {
            authRepository.save(auth);
            authRepository.save(auth2);
            // auth.setPeople(user);
            // auth2.setPeople(user);
            user.setAuths(auth);
            user.setAuths(auth2);
        }
    }
@Transactional(readOnly = true)
    public boolean findByRole(String s) throws UsernameNotFoundException {
        Optional<Role> auth = authRepository.findByRole(s);

        return auth.isEmpty();

    }

    @Transactional
    public Role findRole(String s) throws RuntimeException {
        Optional<Role> auth = authRepository.findByRole(s);
        if (auth.isEmpty()) {
            throw new RuntimeException("Auth not found!");
        }
        return auth.get();


    }


}
