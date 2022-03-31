package org.generation.gamestore.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max=255)
	private String tipo;
	
	@NotNull
	private boolean exportado;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("category")
	private List<product> produtcs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isReceita() {
		return exportado;
	}

	public void setReceita(boolean exportado) {
		this.exportado = exportado;
	}

	public List<product> getProdutcs() {
		return produtcs;
	}

	public void setProdutcs(List<product> produtcs) {
		this.produtcs = produtcs;
	}
	
}
