package SecurityApp.services;

import SecurityApp.models.Role;
import SecurityApp.models.User;

public interface RegistrationService {
     void makeEncode(User user);
     void registerSuperAdmin(User user);
     void registerAAdmin(User user, Role auth1, Boolean isAdmin, Boolean isUser);

}
