import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUserById(Long id ) {
        if (id == 7) {
            User user = new User();
            user.setId(7);
            user.setUsername("Mike");
            user.setFirstName("Kraiwich");
            user.setLastName("Kornsri");
            user.setAge(21);
            user.setGrade(4.00);
            user.setTelephone("99999999");
            user.setGraduatedFrom("BUU");
            user.setMajor("ComSci");
        } else if (id == 1) {
            return new User();
        }
        return null;
    }

}
