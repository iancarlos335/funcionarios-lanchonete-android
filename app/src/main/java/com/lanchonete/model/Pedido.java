package com.lanchonete.model;

import java.util.List;

public class Pedido {

	private long id;
	

	private double valorTotal;

	private List<Bebida> bebida;

	private List<Doce> doce;

	private List<Salgado> salgado;

	private Funcionario funcionario;
	
	//Fiz alterações nas notations, de acordo com o modelo conceitual do banco

}
