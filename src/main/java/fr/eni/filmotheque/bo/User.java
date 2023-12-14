package fr.eni.filmotheque.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("membres")
public class User {
    @Id
    private Long id;

    @Column("prenom")
    private String prenom;
    @Column("nom")
    private String nom;
    @Column("email")
    private String username;

    @Column("password")
    private String password;
    @Column("admin")
    private Boolean admin;



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public User() {
    }
    public User(Long id, String prenom, String nom) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    public User(Long id, String prenom, String nom, String username, String password, Boolean admin) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
