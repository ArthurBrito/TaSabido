package br.ufc.engsoftware.models;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by Thiago on 14/05/2016.
 */
@RealmClass
public class Duvida implements RealmModel {

    private int id;
    private int usuario;
    private int materia;
    private int subtopico;
    private String titulo;
    private String descricao;
    private String username;
    private String data_duvida;

    public String getData_duvida() {
        return data_duvida;
    }

    public void setData_duvida(String data_duvida) {
        this.data_duvida = data_duvida;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Duvida(){}

    public Duvida(int id_duvida, int id_usuario){
        this.id = id_duvida;
        this.usuario = id_usuario;
    }

    public Duvida( int id_usuario, int id_materia, int id_subtopico, String titulo, String descricao){
        this.usuario = id_usuario;
        this.materia = id_materia;
        this.subtopico = id_subtopico;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Duvida(int id_duvida, int id_usuario, int id_materia, int id_subtopico, String titulo, String descricao){
        this.id = id_duvida;
        this.usuario = id_usuario;
        this.materia = id_materia;
        this.subtopico = id_subtopico;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public int getId_duvida() {
        return id;
    }

    public void setId_duvida(int id_duvida) {
        this.id = id_duvida;
    }

    public int getId_materia() {
        return materia;
    }

    public void setId_materia(int id_materia) {
        this.materia = id_materia;
    }

    public int getId_subtopico() {
        return subtopico;
    }

    public void setId_subtopico(int id_subtopico) {
        this.subtopico = id_subtopico;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_usuario() {
        return usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.usuario = id_usuario;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
