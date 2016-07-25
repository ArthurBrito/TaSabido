package br.ufc.engsoftware.Ormlite;

import java.util.Vector;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

/**
 * Created by arthurbrito on 16/05/16.
 */
@RealmClass
public class Perfil extends RealmObject{
    private int id_usuario;
    private String nome;
    private String email;
    private String usuario;

    @Ignore
    String senha;
    int moedas;

    public Perfil(){}


    //construtor pro login
    public Perfil(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public Perfil(String nome, String usuario, String email, String senha){
        this.nome = nome;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

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

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

}
