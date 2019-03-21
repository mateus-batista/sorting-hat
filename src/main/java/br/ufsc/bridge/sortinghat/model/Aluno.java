package br.ufsc.bridge.sortinghat.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Aluno implements Comparable<Aluno> {

	@Id
	private Long id;

	private String email;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_casa")
	private Casa casa;

	public Aluno() {

	}

	public Aluno(String email, Long id, String nome) {
		this.email = email;
		this.id = id;
		this.nome = nome;
	}

	public Aluno(Long id, String email) {
		this.email = email;
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Casa getCasa() {
		return this.casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	@Override
	public int compareTo(Aluno o) {
		return this.nome.compareTo(o.nome);
	}
}
