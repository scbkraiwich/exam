import lombok.Data;

import javax.persistence.Entity;

@Data
public class User {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private Integer age;
    private String telephone;
    private String graduatedFrom;
    private Double grade;
    private String major;
    private String job;

}
