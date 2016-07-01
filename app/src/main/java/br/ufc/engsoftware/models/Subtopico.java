package br.ufc.engsoftware.models;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by limaneto on 21/05/16.
 */

@RealmClass
public class Subtopico extends RealmObject{
    private int id;
    private int materia;
    private String nome;

    public Subtopico(int id_subtopico, int id_materia, String nome_subtopico) {
        this.id = id_subtopico;
        this.materia = id_materia;
        this.nome = nome_subtopico;
    }

    public Subtopico() {
    }

    public int getId_subtopico() {
        return id;
    }

    public void setId_subtopico(int id_subtopico) {
        this.id = id_subtopico;
    }

    public int getId_materia() {
        return materia;
    }

    public void setId_materia(int id_materia) {
        this.materia = id_materia;
    }

    public String getNome_subtopico() {
        return nome;
    }

    public void setNome_subtopico(String nome_subtopico) {
        this.nome = nome_subtopico;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
