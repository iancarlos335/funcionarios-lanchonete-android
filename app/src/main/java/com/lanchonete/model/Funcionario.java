package com.lanchonete.model;



public class Funcionario {

	private long id;

	private String nomeFuncionario;


	public Funcionario() {}

	public Funcionario(String nomeFuncionario) {
		super();
		this.nomeFuncionario = nomeFuncionario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

}
