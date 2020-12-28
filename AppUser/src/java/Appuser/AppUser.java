/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Appuser;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Figro
 */
@Entity
@Table(name = "APPUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM AppUser c"),
    @NamedQuery(name = "Contact.findById", query = "SELECT c FROM AppUser c WHERE c.id = :id"),
    @NamedQuery(name = "Contact.findByUserID", query = "SELECT c FROM AppUser c WHERE c.userid = :userid"),
    @NamedQuery(name = "Contact.findByPassword", query = "SELECT c FROM AppUser c WHERE c.password = :password"),
    @NamedQuery(name = "Contact.findByGroupName", query = "SELECT c FROM AppUser c WHERE c.groupname = :groupname"),  
})
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Column(name = "USERID")
    @Size(max = 255)
    private String userid;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "GROUPNAME")
    private String groupname;

    public AppUser() {
    }

    public AppUser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userid;
    }

    public void setUserID(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Instance<? extends PasswordHash> instance;
        instance = CDI.current().select(Pbkdf2PasswordHash.class);
     PasswordHash passwordHash = instance.get();
     passwordHash.initialize(new HashMap<String,String>());
     password = passwordHash.generate(password.toCharArray());
    }

    public String getGroupName() {
        return groupname;
    }

    public void setGroupName(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Appuser.AppUser[ id=" + id + " ]";
    }
    
}
