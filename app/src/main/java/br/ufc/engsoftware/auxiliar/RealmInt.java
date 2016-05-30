package br.ufc.engsoftware.auxiliar;

import io.realm.RealmObject;

/**
 * Created by limaneto on 29/05/16.
 */
public class RealmInt extends RealmObject {
    private int id_subtopico;

    public int getId_Subtopico() {
        return id_subtopico;
    }

    public void setId_subtopico(int id_subtopico) {
        this.id_subtopico = id_subtopico;
    }
}