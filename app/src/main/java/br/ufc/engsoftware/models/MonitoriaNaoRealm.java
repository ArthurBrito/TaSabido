package br.ufc.engsoftware.models;

import java.util.List;
import java.util.Vector;

import br.ufc.engsoftware.auxiliar.RealmInt;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**

 * Created by limaneto on 27/05/16.
 */
public class MonitoriaNaoRealm{

    private int id, id_subtopico, usuario, materia;
    private List<Integer> subtopico;
    private String titulo, descricao, data_monitoria, endereco, dia, horario, username;

    public MonitoriaNaoRealm(){}

    public MonitoriaNaoRealm(int monitoria, int usuario){
        this.id = monitoria;
        this.usuario = usuario;
    }

    public MonitoriaNaoRealm(int usuario, int materia, int subtopico_selecionado, String titulo, String descricao, String data, String endereco) {
        this.usuario = usuario;
        this.materia = materia;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_monitoria = data;
        this.id_subtopico = subtopico_selecionado;
        this.endereco = endereco;
    }

    public MonitoriaNaoRealm(int usuario, String titulo, String descricao, String data, String endereco) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_monitoria = data;
        this.endereco = endereco;
    }


    public int getId_usuario() {
        return usuario;
    }

    public void setId_usuario(int usuario) {
        this.usuario = usuario;
    }

    public MonitoriaNaoRealm(int monitoria, int usuario, int materia, int ids_subtopico_selecionado, String titulo, String descricao, String data, String endereco) {
        this.usuario = usuario;
        this.id = monitoria;
        this.materia = materia;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_monitoria = data;
        this.id_subtopico = ids_subtopico_selecionado;
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

    public List<Integer> getSubtopico(){ return this.subtopico; }

    public void setSubtopico(List<Integer> subtopicos){ this.subtopico = subtopicos;}

    public int getId_subtopico() {
        return id_subtopico;
    }

    public void setId_subtopico(int subtopico) {
        this.id_subtopico = subtopico;
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