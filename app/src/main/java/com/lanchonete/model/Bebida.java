package com.lanchonete.model;

public class Bebida {

	private long id;
	

	private String nomeBebida;
	

	private double valor;
	
	public Bebida() {}
	
	public Bebida(String nomeBebida, double valor) {
		super();
		this.nomeBebida = nomeBebida;
		this.valor = valor;
	}

	public String getNomeBebida() {
		return nomeBebida;
	}

	public void setNomeBebida(String nomeBebida) {
		this.nomeBebida = nomeBebida;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
