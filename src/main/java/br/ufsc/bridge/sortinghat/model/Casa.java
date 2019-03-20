package br.ufsc.bridge.sortinghat.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Casa {

	@Id
	private Long id;
	private String nome;
	private String descricao ;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
