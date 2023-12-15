package fr.eni.filmotheque.bo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Avis {

    @Id
    private int id;

    @Column("note")
    @Min(0)
    @Max(5)
    @NotNull(message = "La note est obligatoire !")
    private int note;
    @Column("commentaire")
    @NotNull(message = "Merci de mettre une note comprise entre {min} et {max}")
    private String commentaire;

    @Column("id_membre")
    private User user;

    @Column("id_film")
    private Film film;

    @Override
    public String toString() {
        return "Avis :" +
                "note :" + note +
                ", commentaire :'" + commentaire + '\'' +
                user.getPrenom() +
                film.getId()+
                '}';
    }

    public Avis() {
    }

    public Avis(int id, int note, String commentaire, User user, Film film) {
        this.id = id;
        this.note = note;
        this.commentaire = commentaire;
        this.user = user;
        this.film = film;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
