package by.javateam.model;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "LAST_NAME")
    @Pattern(regexp = "^[A-Za-z][a-z]+$")
    @Size(max = 20, message = "value lastName - exceeds the permissible value")
    private String lastName;

    @Column(name = "FIRST_NAME")
    @Pattern(regexp = "^[A-Za-z][a-z]+$")
    @Size(max = 20, message = "value firstName - exceeds the permissible value")
    private String firstName;

    @Min(1)
    @Max(99)
    @Column(name = "age")
    private int age;

    @Column(name = "SEX")
    @Pattern(regexp = "^(fe)?male$", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String sex;

    @Column(name = "CITY")
    @Pattern(regexp = "^[A-Za-z/-]+$")
    @Size(max = 20, message = "value city - exceeds the permissible value")
    private String city;

    @Min(1)
    @Max(999999)
    @Column(name = "INCOME")
    private int income;

    @Column(name = "TIME_CREATE_USER")
    private LocalDateTime createdTimestamp; //TODO change to string

    @Column(name = "TIME_UPDATE_USER")
    private LocalDateTime modifiedTimestamp;

    public User() {
    }

    public User(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public User(String lastName, String firstName, int age, String sex, String city, int income) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.sex = sex;
        this.city = city;
        this.income = income;
    }

    public User(String lastName, String firstName, int age, String sex, String city, int income, LocalDateTime createdTimestamp, LocalDateTime modifiedTimestamp) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.sex = sex;
        this.city = city;
        this.income = income;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public void setModifiedTimestamp(LocalDateTime modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", city='" + city + '\'' +
                ", income=" + income +
                ", createdTimestamp=" + createdTimestamp +
                ", modifiedTimestamp=" + modifiedTimestamp +
                '}';
    }



}