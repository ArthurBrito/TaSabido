package br.ufc.engsoftware.models;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by Thiago on 14/05/2016.
 */
@RealmClass
public class Duvida implements RealmModel {

    private int id_duvida;
    private int id_usuario;
    private int id_materia;
    private int id_subtopico;
    private String titulo;
    private String descricao;

    public Duvida(){}

    public Duvida( int id_usuario, int id_materia, int id_subtopico, String titulo, String descricao){
        this.id_usuario = id_usuario;
        this.id_materia = id_materia;
        this.id_subtopico = id_subtopico;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Duvida(int id_duvida, int id_usuario, int id_materia, int id_subtopico, String titulo, String descricao){
        this.id_duvida = id_duvida;
        this.id_usuario = id_usuario;
        this.id_materia = id_materia;
        this.id_subtopico = id_subtopico;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public int getId_duvida() {
        return id_duvida;
    }

    public void setId_duvida(int id_duvida) {
        this.id_duvida = id_duvida;
    }

    public int getId_materia() {
        return id_materia;
    }

    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }

    public int getId_subtopico() {
        return id_subtopico;
    }

    public void setId_subtopico(int id_subtopico) {
        this.id_subtopico = id_subtopico;
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
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
