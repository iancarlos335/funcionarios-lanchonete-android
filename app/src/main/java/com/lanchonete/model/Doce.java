package com.lanchonete.model;

public class Doce {

	private long id;

	private String nomeDoce;

	private double valor;

	private String descricao;

	private String imagem;

	public Doce() {}

	public Doce(String nomeDoce, double valor, String descricao) {
		super();
		this.nomeDoce = nomeDoce;
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

	public String getNomeDoce() {
		return nomeDoce;
	}

	public void setNomeDoce(String nomeDoce) {
		this.nomeDoce = nomeDoce;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}


}
