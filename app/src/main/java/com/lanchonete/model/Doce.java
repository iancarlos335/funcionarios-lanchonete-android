package com.lanchonete.model;

public class Doce {

	private long id;
	

	private String nomeDoce;
	

	private double valor;
	
	public Doce() {}

	public Doce(String nomeDoce, double valor) {
		super();
		this.nomeDoce = nomeDoce;
		this.valor = valor;
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
	
	
	
	
}
