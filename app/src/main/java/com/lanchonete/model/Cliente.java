package com.lanchonete.model;


public class Cliente {


    private long id;

    private String nomeCliente;

    private String cpfCliente;

    private String enderecoCliente;

    public Cliente(){

    }

    public Cliente(String nomeCliente, String cpfCliente , String enderecoCliente){
        super();
        this.nomeCliente = nomeCliente;
        this.cpfCliente = cpfCliente;
        this.enderecoCliente = enderecoCliente;
    }

    //Get e Set CPF do Cliente
    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    //Get e Set nome do Cliente
    public String getNomeCliente(){
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente){
        this.nomeCliente = nomeCliente;
    }

    //Get e Set CPF do Cliente
    public String getCpfCliente(){
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente){
        this.cpfCliente = cpfCliente;
    }

    //Get e Set endereço do Cliente
    public String getEnderecoCliente(){
        return enderecoCliente;
    }

    public void setEnderecoCliente(String enderecoCliente){
        this.enderecoCliente = enderecoCliente;
    }

}