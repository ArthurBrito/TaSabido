package br.ufc.engsoftware.models;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by limaneto on 21/05/16.
 */

@RealmClass
public class Subtopico extends RealmObject{
    private int id_subtopico;
    private int id_materia;
    private String nome_subtopico;

    public Subtopico(int id_subtopico, int id_materia, String nome_subtopico) {
        this.id_subtopico = id_subtopico;
        this.id_materia = id_materia;
        this.nome_subtopico = nome_subtopico;
    }

    public Subtopico() {
    }

    public int getId_subtopico() {
        return id_subtopico;
    }

    public void setId_subtopico(int id_subtopico) {
        this.id_subtopico = id_subtopico;
    }

    public int getId_materia() {
        return id_materia;
    }

    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }

    public String getNome_subtopico() {
        return nome_subtopico;
    }

    public void setNome_subtopico(String nome_subtopico) {
        this.nome_subtopico = nome_subtopico;
    }

    @Override
    public String toString() {
        return this.nome_subtopico;
    }
}
