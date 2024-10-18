package SecurityApp.services;


import SecurityApp.models.User;
import SecurityApp.repositories.RoleRepository;
import SecurityApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

    private final UserRepository peopleRepository;


    @Autowired
    public UserServiceImp(UserRepository peopleRepository, RoleRepository authRepository) {
        this.peopleRepository = peopleRepository;

    }

    public List<User> findAll() {
        return peopleRepository.findAll();
    }

    public User findOne(int id) {
        Optional<User> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

    }

    @Transactional
    public void save(User user) {
        peopleRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedPerson) {
        Optional<User> foundPerson = peopleRepository.findById(id);
        foundPerson.orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        updatedPerson.setId(id);

        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        Optional<User> foundPerson = peopleRepository.findById(id);
         foundPerson.orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        peopleRepository.deleteById(id);
    }


}
