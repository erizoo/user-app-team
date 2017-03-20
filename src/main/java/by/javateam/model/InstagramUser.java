package by.javateam.model;

import javax.persistence.*;

@Entity
@Table(name = "instagram", schema = "test")
public class InstagramUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ID_INSTAGRAM")
    private String idInstagram;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "NICK_NAME")
    private String nickName;

    public InstagramUser() {
    }

    public InstagramUser(String idInstagram, String fullName, String nickName) {
        this.idInstagram = idInstagram;
        this.fullName = fullName;
        this.nickName = nickName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdInstagram() {
        return idInstagram;
    }

    public void setIdInstagram(String idInstagram) {
        this.idInstagram = idInstagram;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}

