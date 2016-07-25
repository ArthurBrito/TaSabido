
package br.ufc.engsoftware.Ormlite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@Generated("org.jsonschema2pojo")
@DatabaseTable(tableName = "subtopico")
public class Subtopico {

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private Integer id;
    @SerializedName("nome")
    @Expose
    @DatabaseField
    private String nome;
    @DatabaseField(foreign = true)
    private Materia materia;
    @SerializedName("duvidas")
    @Expose
    @ForeignCollectionField
    private Collection<Duvida> duvidas = new ArrayList<Duvida>();
    @SerializedName("monitorias")
    @Expose
    @ForeignCollectionField
    private Collection<Monitoria> monitorias = new ArrayList<Monitoria>();

    public Subtopico() {
    }

    public Subtopico(Integer id, String nome, Materia materia, Collection<Duvida> duvidas, Collection<Monitoria> monitorias) {
        this.id = id;
        this.nome = nome;
        this.materia = materia;
        this.duvidas = duvidas;
        this.monitorias = monitorias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Collection<Duvida> getDuvidas() {
        return duvidas;
    }

    public void setDuvidas(Collection<Duvida> duvidas) {
        this.duvidas = duvidas;
    }

    public Collection<Monitoria> getMonitorias() {
        return monitorias;
    }

    public void setMonitorias(Collection<Monitoria> monitorias) {
        this.monitorias = monitorias;
    }

    @Override
    public String toString() {
        return nome;
    }
}
