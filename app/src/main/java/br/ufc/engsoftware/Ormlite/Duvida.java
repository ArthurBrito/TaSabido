
package br.ufc.engsoftware.Ormlite;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Generated("org.jsonschema2pojo")
@DatabaseTable(tableName = "duvida")
public class Duvida {

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField(foreign = true)
    private Subtopico subtopico;
    @SerializedName("titulo")
    @Expose
    @DatabaseField
    private String titulo;
    @SerializedName("descricao")
    @Expose
    @DatabaseField
    private String descricao;
    @SerializedName("data_duvida")
    @Expose
    @DatabaseField
    private String dataDuvida;
    @SerializedName("first_name")
    @Expose
    @DatabaseField
    private String firstName;
    @SerializedName("usuario")
    @Expose
    @DatabaseField
    private Integer usuario;

    public Duvida() {
    }

    public Duvida(Integer id, Subtopico subtopico, String titulo, String descricao, String dataDuvida, String firstName, Integer usuario) {
        this.id = id;
        this.subtopico = subtopico;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataDuvida = dataDuvida;
        this.firstName = firstName;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subtopico getSubtopico() {
        return subtopico;
    }

    public void setSubtopico(Subtopico subtopico) {
        this.subtopico = subtopico;
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

    public String getDataDuvida() {
        return dataDuvida;
    }

    public void setDataDuvida(String dataDuvida) {
        this.dataDuvida = dataDuvida;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
