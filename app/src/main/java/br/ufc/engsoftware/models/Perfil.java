package br.ufc.engsoftware.models;

/**
 * Created by arthurbrito on 16/05/16.
 */
public class Perfil {
    String nome;
    String senha;
    String email;
    int moedas;

    public Perfil(String nome, String email){
        this.nome = nome;
        this.email = email;
    }

    public Perfil(String nome, String email, String senha){
        this.nome = nome;
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

        return email;
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
}
