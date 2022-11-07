package com.lanchonete.model;

public class Bebida { //interessante

    private long id;

    private String nomeBebida;

    private double valor;

    private String descricao;

    private String imagem;

    public Bebida() {
    }

    public Bebida(String nomeBebida, double valor, String descricao, String imagem) {
        super();
        this.nomeBebida = nomeBebida;
        this.valor = valor;
        this.descricao = descricao;
        this.imagem  = imagem;
    }


    public String getImagem() { //se eu n adicionar esse recurso de construção no get, eu n vou conseguir acessar pelo android
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
