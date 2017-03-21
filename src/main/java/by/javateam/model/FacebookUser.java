package by.javateam.model;

import javax.persistence.*;

/**
 * Created by valera.
 */
@Entity
@Table(name = "facebook")
public class FacebookUser {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "FULL_NAME")
    private String fullName;

    public FacebookUser() {
    }

    public FacebookUser(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "FacebookUser{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
