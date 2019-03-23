package evg299.sample.web.dojo.domain.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Evgeny on 06.01.2015.
 */
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recordUuid;
    private Date lastUpdate;
    private String fname;
    private String sname;
    private String lname;
    private String email;
    private Boolean gender;
    private Date birthDate;

    @PrePersist
    void prePersist()
    {
        lastUpdate = new Date();
        recordUuid = UUID.randomUUID().toString();
    }

    @PreUpdate
    void preUpdate()
    {
        lastUpdate = new Date();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRecordUuid()
    {
        return recordUuid;
    }

    public Date getLastUpdate()
    {
        return lastUpdate;
    }

    public String getFname()
    {
        return fname;
    }

    public void setFname(String fname)
    {
        this.fname = fname;
    }

    public String getSname()
    {
        return sname;
    }

    public void setSname(String sname)
    {
        this.sname = sname;
    }

    public String getLname()
    {
        return lname;
    }

    public void setLname(String lname)
    {
        this.lname = lname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Boolean getGender()
    {
        return gender;
    }

    public void setGender(Boolean gender)
    {
        this.gender = gender;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }
}
