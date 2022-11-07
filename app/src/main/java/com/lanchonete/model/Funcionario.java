package com.lanchonete.model;

public class Funcionario {

    private long id;

    private String nomeFuncionario;

    private String imagem;

    public Funcionario() {}

    public Funcionario(String nomeFuncionario, String imagem) {
        super();
        this.nomeFuncionario = nomeFuncionario;
        this.imagem = imagem;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
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