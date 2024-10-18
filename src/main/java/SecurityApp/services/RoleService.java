package SecurityApp.services;

import SecurityApp.models.Role;
import SecurityApp.models.User;

public interface RoleService {
     boolean findByRole(String s);
     void addRolesToTable(User user);
     Role findRole(String s);

}
