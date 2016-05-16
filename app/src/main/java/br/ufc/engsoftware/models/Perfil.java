package br.ufc.engsoftware.models;

/**
 * Created by arthurbrito on 16/05/16.
 */
public class Perfil {
    String nome;
    String email;
    int moedas;

    public int getMoedas() {
        return moedas;
    }

    public void setMoedas(int moedas) {
        this.moedas = moedas;
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
