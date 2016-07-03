package br.ufc.engsoftware.Ormlite;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

/**

 * Created by limaneto on 27/05/16.
 */

@DatabaseTable(tableName = "monitoria")
public class Monitoria {

    @DatabaseField(id = true)
    private int id;

//    @DatabaseField(dataType = DataType.SERIALIZABLE)
//    private ArrayList<IdSubtopico> subtopico;

    @DatabaseField
    private int usuario;
    @DatabaseField
    private int materia;
    @DatabaseField
    private int subtopico;

    @DatabaseField
    private String titulo;
    @DatabaseField
    private String descricao;
    @DatabaseField
    private String data_monitoria;
    @DatabaseField
    private String endereco;
    @DatabaseField
    private String dia;
    @DatabaseField
    private String horario;
    @DatabaseField
    private String username;

    public Monitoria(){}

    public Monitoria(int monitoria, int usuario){
        this.id = monitoria;
        this.usuario = usuario;
    }

    public Monitoria(int usuario, int materia, int subtopico, String titulo, String descricao, String data, String dia, String hora, String endereco) {
        this.usuario = usuario;
        this.materia = materia;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_monitoria = data;
        this.dia = dia;
        this.horario = hora;
        this.subtopico = subtopico;
        this.endereco = endereco;
    }

    public Monitoria(int usuario, String titulo, String descricao, String data, String endereco) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_monitoria = data;
        this.endereco = endereco;
    }

    public Monitoria(int id_monitoria, int usuario, int materia, int subtopico, String titulo, String descricao, String data, String dia, String horario, String endereco) {
        this.usuario = usuario;
        this.id = id_monitoria;
        this.materia = materia;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_monitoria = data;
        this.username = username;
        this.dia = dia;
        this.horario = horario;
        this.subtopico = subtopico;
        this.endereco = endereco;
    }


    public int getId_usuario() {
        return usuario;
    }

    public void setId_usuario(int usuario) {
        this.usuario = usuario;
    }


    public Monitoria(int monitoria, int usuario, int materia, int subtopico, String titulo, String username, String descricao, String data, String dia, String hora, String endereco) {
        this.usuario = usuario;
        this.id = monitoria;
        this.materia = materia;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_monitoria = data;
        this.username = username;
        this.dia = dia;
        this.horario = hora;
        this.subtopico = subtopico;
        this.endereco = endereco;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data_monitoria;
    }

    public void setData(String data) {
        this.data_monitoria = data;
    }

    public int getId_materia() {
        return materia;
    }

    public void setId_materia(int materia) {
        this.materia = materia;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_monitoria() {
        return this.id;
    }

    public void setId_monitoria(int monitoria) {
        this.id = monitoria;
    }

    public String toString(){
        return this.titulo;
    }

    public int getId_subtopicos() {
        return subtopico;
    }

    public void setId_subtopico(int subtopicos) {
        this.subtopico = subtopico;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDia() {
        return dia;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getHorario() {
        return horario;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}