package fr.eni.filmotheque.bo;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Table("films")
public class Film {
    @Id
    private int id;
    @NotBlank(message = "{film.titre.notBlank}")
    @Size(min = 1, max = 50, message = "{film.titre.size}")
    @Column("titre")
    private String titre;
    @NotBlank(message = "Le synopsis ne peut pas être vide")
    @Size(min = 20, max = 250, message = "Le synopsis doit être entre {min} et {max} caractères")
    @Column("synopsis")
    private String synopsis;
    @Min(value = 1900, message = "Merci de renseigner une année valide (supérieure à 1900)")
    @Max(value = 2023, message = "Merci de renseigner une année valide (de cette année ou avant)")
    @Column("annee")
    private int annee;

    @Min(1)
    @Max(240)
    private int duree;

    @Column("id_realisateur")
    @NotNull(message = "Merci de sélectionner un réalisateur")
    private Participant realisateur;
    @Column("id_acteur")
    @Nullable
    private List<Participant> acteurs;
    @Column("id_genre")
    @NotNull(message = "Merci de sélectionner le genre du film")
    private Genre genre;

    @Override
    public String toString() {
        return "Film " + id + '\n' +
                "Titre: " + titre + "[annee : " + annee + ", duree :" + duree + "]" + '\n' +
                "Synopsis : " + synopsis + '\n' +
                realisateur + '\n' +
                acteurs + '\n' +
                "Genre : " + genre.getLibelle() + '\n';

    }

    /**
     * Méthode pour récupérer la liste des réalisateurs du film
     *
     * @return
     */
    public String getRealisateursString() {
        String realisateurString = "";
        realisateurString += realisateur.getPrenom() + " " + realisateur.getNom() + ". ";


        return realisateurString;
    }

    /**
     * Méthode pour récupérer la liste des acteurs du film
     *
     * @return
     */
    public String getActeursString() {
        String acteursString = "";
        for (Participant acteur : acteurs) {
            acteursString += acteur.getPrenom() + " " + acteur.getNom() + ". ";

        }
        return acteursString;
    }


    public Film() {
        this.acteurs = new ArrayList<>();
    }

    public Film(int id, String titre, String synopsis, int annee, int duree, Participant realisateur, List<Participant> acteurs, Genre genre) {
        this.id = id;
        this.titre = titre;
        this.synopsis = synopsis;
        this.annee = annee;
        this.duree = duree;
        this.realisateur = realisateur;
        this.acteurs = acteurs;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public Participant getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(Participant realisateur) {
        this.realisateur = realisateur;
    }

    @Nullable
    public List<Participant> getActeurs() {
        return acteurs;
    }

    public void setActeurs(@Nullable List<Participant> acteurs) {
        this.acteurs = acteurs;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
