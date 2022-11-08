package com.lanchonete.model;

import java.util.List;

public class Pedido {

    private long id;

    private double valorTotal;

    private List<String> bebidas;

    private List<String> doces;

    private List<String> salgados;

    private String funcionario;

    private String cliente;

    public Pedido() {}

    public Pedido(double valorTotal, List<String> bebidas, List<String> doces, List<String> salgados, String funcionario, String cliente) {
        super();
        this.valorTotal = valorTotal;
        this.bebidas = bebidas;
        this.doces = doces;
        this.salgados = salgados;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }



    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<String> getBebida() {
        return bebidas;
    }

    public void setBebida(List<String> bebida) {
        this.bebidas = bebida;
    }

    public List<String> getDoce() {
        return doces;
    }

    public void setDoce(List<String> doce) {
        this.doces = doce;
    }

    public List<String> getSalgado() {
        return salgados;
    }

    public void setSalgado(List<String> salgado) {
        this.salgados = salgado;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }
}