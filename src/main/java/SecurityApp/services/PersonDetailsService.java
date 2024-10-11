package SecurityApp.services;

import SecurityApp.models.User;
import SecurityApp.models.Auth;
import SecurityApp.repositories.RoleRepository;
import SecurityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import SecurityApp.security.PersonDetails;

import java.util.Optional;


@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;
    private final RoleRepository authRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository, RoleRepository authRepository) {
        this.peopleRepository = peopleRepository;
        this.authRepository = authRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> person = peopleRepository.findByEmail(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new PersonDetails(person.get());
    }


    @Transactional
    public Auth findRole(String s) throws UsernameNotFoundException {
        Optional<Auth> auth = authRepository.findByRole(s);
        if (auth.isEmpty())
            throw new UsernameNotFoundException("Auth not found!");

        return auth.get();


    }

}
