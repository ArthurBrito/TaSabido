package br.ufc.engsoftware.models;

/**
 * Created by arthurbrito on 16/05/16.
 */
public class Perfil {
    String nome;
    String senha;
    String email;
    String usuario;
    int moedas;

    //construtor pro login
    public Perfil(String usuario, String senha){
        this.usuario = usuario;
        this.senha = senha;
    }

    public Perfil(String nome, String usuario, String email, String senha){
        this.nome = nome;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

//    public int getMoedas() {
//        return moedas;
//    }
//
//    public void setMoedas(int moedas) {
//        this.moedas = moedas;
//    }

    public String getSenha() {

        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {

        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
