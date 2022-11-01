package com.lanchonete.model;



public class Salgado {

	private long id;

	private String nomeSalgado;

	private double valor;

	private String descricao;

	public Salgado() {}

	public Salgado(String nomeSalgado, double valor, String descricao) {
		super();
		this.nomeSalgado = nomeSalgado;
		this.valor = valor;
		this.descricao = descricao;
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeSalgado() {
		return nomeSalgado;
	}

	public void setNomeSalgado(String nomeSalgado) {
		this.nomeSalgado = nomeSalgado;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
}
