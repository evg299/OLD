package evg299.sample.web.dojo.dto;

import evg299.sample.web.dojo.domain.entities.User;

import java.util.Date;

/**
 * Created by evgeny on 27.11.14.
 */
public class UserDto {
    private int id;
    private String name;
    private String email;
    private Date birthDate;

    public UserDto() {
    }

    public UserDto(int id, String name, String email, Date birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public UserDto(User user)
    {
        this.id = user.getId().intValue();
        this.name = user.getFname();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString()
    {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
