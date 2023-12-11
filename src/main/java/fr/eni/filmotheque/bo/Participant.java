package fr.eni.filmotheque.bo;

import fr.eni.filmotheque.bo.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;
@Table("participants")
public class Participant {
	@Id
	private int id;
	@Column("prenom")
	private String prenom;
	@Column("nom")
	private String nom;
	private Role[] roles;

	public Participant() {
	}
	public Participant(int id, String prenom, String nom) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
	}

	public Participant(int id, String prenom, String nom, Role[] roles) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return id + " " + prenom + " " + nom;
	}


	public Role[] getRoles() {
		return roles;
	}

	public void setRoles(Role[] roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
}
