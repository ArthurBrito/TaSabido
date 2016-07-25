
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
@DatabaseTable(tableName = "materia")
public class Materia {

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private Integer id;
    @SerializedName("nome")
    @Expose
    @DatabaseField
    private String nome;
    @DatabaseField(foreign = true)
    private Curso curso;
    @SerializedName("subtopicos")
    @Expose
    @ForeignCollectionField
    private Collection<Subtopico> subtopicos = new ArrayList<Subtopico>();

    public Materia(){}

    public Materia(Integer id, String nome, Curso curso, Collection<Subtopico> subtopicos) {
        this.id = id;
        this.nome = nome;
        this.curso = curso;
        this.subtopicos = subtopicos;
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Collection<Subtopico> getSubtopicos() {
        return subtopicos;
    }

    public void setSubtopicos(Collection<Subtopico> subtopicos) {
        this.subtopicos = subtopicos;
    }

    @Override
    public String toString() {
        return nome;
    }
}
