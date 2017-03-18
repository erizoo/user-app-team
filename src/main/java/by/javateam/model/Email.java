package by.javateam.model;

import javax.persistence.*;

@Entity
@Table(name = "mail")
public class Email {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "SUBJECT_EMAIL")
    private String subject;

    @Column(name = "BODY_EMAIL")
    private String body;

    @Column(name = "FROM_EMAIL")
    private String from;

    @Column(name = "TIME_EMAIL")
    private String createdTimestamp;

    public Email() {
    }

    public Email(String subject, String body, String from, String createdTimestamp) {
        this.subject = subject;
        this.body = body;
        this.from = from;
        this.createdTimestamp = createdTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

}
