package br.ufc.engsoftware.models;

import java.util.Vector;

import br.ufc.engsoftware.auxiliar.RealmInt;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**

 * Created by limaneto on 27/05/16.
 */
@RealmClass
public class Monitoria extends RealmObject {

    private int id_subtopico , id_monitoria, id_usuario, id_materia;
    private String titulo, descricao, data, endereco;

    public Monitoria(){}

    public Monitoria(int id_monitoria, int id_usuario){
        this.id_monitoria = id_monitoria;
        this.id_usuario = id_usuario;
    }

    public Monitoria(int id_usuario, int id_materia, int id_subtopico_selecionado, String titulo, String descricao, String data, String endereco) {
        this.id_usuario = id_usuario;
        this.id_materia = id_materia;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.id_subtopico = id_subtopico_selecionado;
        this.endereco = endereco;
    }

    public Monitoria(int id_usuario, String titulo, String descricao, String data, String endereco) {
        this.id_usuario = id_usuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.endereco = endereco;
    }


    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Monitoria(int id_monitoria, int id_usuario, int id_materia, int ids_subtopico_selecionado, String titulo, String descricao, String data, String endereco) {
        this.id_usuario = id_usuario;
        this.id_monitoria = id_monitoria;
        this.id_materia = id_materia;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
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
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId_materia() {
        return id_materia;
    }

    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_monitoria() {
        return this.id_monitoria;
    }

    public void setId_monitoria(int id_monitoria) {
        this.id_monitoria = id_monitoria;
    }

    public String toString(){
        return this.titulo;
    }

    public int getId_subtopico() {
        return id_subtopico;
    }

    public void setId_subtopico(int id_subtopico) {
        this.id_subtopico = id_subtopico;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}