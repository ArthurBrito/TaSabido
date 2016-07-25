
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
@DatabaseTable(tableName = "curso")
public class Curso {

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private Integer id;
    @SerializedName("nome")
    @Expose
    @DatabaseField
    private String nome;
    @SerializedName("materias")
    @Expose
    @ForeignCollectionField
    private Collection<Materia> materias = new ArrayList<Materia>();

    public Curso() {
    }

    public Curso(Integer id, String nome, Collection<Materia> materias) {
        this.id = id;
        this.nome = nome;
        this.materias = materias;
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

    public Collection<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(Collection<Materia> materias) {
        this.materias = materias;
    }

    @Override
    public String toString() {
        return nome;
    }


}
