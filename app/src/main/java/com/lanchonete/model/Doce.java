package com.lanchonete.model;

public class Doce {

	private long id;

	private String nomeDoce;

	private double valor;

	private String descricao;

	private String imagem;

    public Doce(String nomeDoce, double valor, String descricao, String imagem) {
		super();
		this.nomeDoce = nomeDoce;
		this.valor = valor;
		this.descricao = descricao;
		this.imagem = imagem;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}


}
