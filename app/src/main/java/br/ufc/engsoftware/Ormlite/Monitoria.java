
package br.ufc.engsoftware.Ormlite;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Generated("org.jsonschema2pojo")
@DatabaseTable(tableName = "monitoria")
public class Monitoria {

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
    @SerializedName("data_monitoria")
    @Expose
    @DatabaseField
    private String dataMonitoria;
    @SerializedName("dia")
    @Expose
    @DatabaseField
    private String dia;
    @SerializedName("horario")
    @Expose
    @DatabaseField
    private String horario;
    @SerializedName("first_name")
    @Expose
    @DatabaseField
    private String firstName;
    @SerializedName("endereco")
    @Expose
    @DatabaseField
    private String endereco;
    @SerializedName("usuario")
    @Expose
    @DatabaseField
    private Integer usuario;

    public Monitoria() {
    }

    public Monitoria(Integer id, Subtopico subtopico, String titulo, String descricao, String dataMonitoria, String dia, String horario, String firstName, String endereco, Integer usuario) {
        this.id = id;
        this.subtopico = subtopico;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataMonitoria = dataMonitoria;
        this.dia = dia;
        this.horario = horario;
        this.firstName = firstName;
        this.endereco = endereco;
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

    public String getDataMonitoria() {
        return dataMonitoria;
    }

    public void setDataMonitoria(String dataMonitoria) {
        this.dataMonitoria = dataMonitoria;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
