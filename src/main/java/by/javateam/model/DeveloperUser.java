package by.javateam.model;

import javax.persistence.*;

/**
 * Created by Erizo.
 */
@Entity
@Table(name = "developers")
public class DeveloperUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    public DeveloperUser() {
    }

    public DeveloperUser(String name, String lastName, String mobilePhone, String position, String login, String password) {
        this.name = name;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.position = position;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
