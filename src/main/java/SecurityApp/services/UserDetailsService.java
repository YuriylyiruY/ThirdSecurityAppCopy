package SecurityApp.services;

import SecurityApp.models.User;
import SecurityApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import SecurityApp.security.UserDetails;

import java.util.Optional;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository peopleRepository;


    @Autowired
    public UserDetailsService(UserRepository peopleRepository) {
        this.peopleRepository = peopleRepository;

    }

    @Override
    @Transactional
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> person = peopleRepository.findByEmail(s);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new UserDetails(person.get());
    }


}
