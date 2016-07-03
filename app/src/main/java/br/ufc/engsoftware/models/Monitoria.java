//package br.ufc.engsoftware.models;
//
//import java.util.List;
//import java.util.Vector;
//
//import br.ufc.engsoftware.auxiliar.RealmInt;
//import br.ufc.engsoftware.retrofit.IdSubtopico;
//import io.realm.RealmList;
//import io.realm.RealmObject;
//import io.realm.annotations.PrimaryKey;
//import io.realm.annotations.RealmClass;
//
///**
//
// * Created by limaneto on 27/05/16.
// */
//@RealmClass
//public class Monitoria extends RealmObject {
//
//    @PrimaryKey
//    private int id;
//
//    private RealmList<IdSubtopico> subtopico;
//    private int usuario, materia;
//    private String titulo, descricao, data_monitoria, endereco;
//    private String dia, horario;
//    private String username;
//
//    public Monitoria(){}
//
//    public Monitoria(int monitoria, int usuario){
//        this.id = monitoria;
//        this.usuario = usuario;
//    }
//
//    public Monitoria(int usuario, int materia, RealmList<IdSubtopico> subtopicos, String titulo, String descricao, String data, String dia, String hora, String endereco) {
//        this.usuario = usuario;
//        this.materia = materia;
//        this.titulo = titulo;
//        this.descricao = descricao;
//        this.data_monitoria = data;
//        this.dia = dia;
//        this.horario = hora;
//        this.subtopico = subtopicos;
//        this.endereco = endereco;
//    }
//
//    public Monitoria(int usuario, String titulo, String descricao, String data, String endereco) {
//        this.usuario = usuario;
//        this.titulo = titulo;
//        this.descricao = descricao;
//        this.data_monitoria = data;
//        this.endereco = endereco;
//    }
//
//    public Monitoria(int id_monitoria, int id_usuario, int id_materia, RealmList<IdSubtopico> subtopicos, String titulo, String descricao, String data, String dia, String horario, String endereco) {
//        this.usuario = usuario;
//        this.id = id_monitoria;
//        this.materia = materia;
//        this.titulo = titulo;
//        this.descricao = descricao;
//        this.data_monitoria = data;
//        this.username = username;
//        this.dia = dia;
//        this.horario = horario;
//        this.subtopico = subtopicos;
//        this.endereco = endereco;
//    }
//
//
//    public int getId_usuario() {
//        return usuario;
//    }
//
//    public void setId_usuario(int usuario) {
//        this.usuario = usuario;
//    }
//
//    public Monitoria(int monitoria, int usuario, int materia, RealmList<IdSubtopico> subtopicos, String titulo, String descricao, String username, String data, String dia, String hora, String endereco) {
//        this.usuario = usuario;
//        this.id = monitoria;
//        this.materia = materia;
//        this.titulo = titulo;
//        this.descricao = descricao;
//        this.data_monitoria = data;
//        this.username = username;
//        this.dia = dia;
//        this.horario = hora;
//        this.subtopico = subtopicos;
//        this.endereco = endereco;
//    }
//
//
//    public String getTitulo() {
//        return titulo;
//    }
//
//    public void setTitulo(String titulo) {
//        this.titulo = titulo;
//    }
//
//    public String getData() {
//        return data_monitoria;
//    }
//
//    public void setData(String data) {
//        this.data_monitoria = data;
//    }
//
//    public int getId_materia() {
//        return materia;
//    }
//
//    public void setId_materia(int materia) {
//        this.materia = materia;
//    }
//
//
//    public String getDescricao() {
//        return descricao;
//    }
//
//    public void setDescricao(String descricao) {
//        this.descricao = descricao;
//    }
//
//    public int getId_monitoria() {
//        return this.id;
//    }
//
//    public void setId_monitoria(int monitoria) {
//        this.id = monitoria;
//    }
//
//    public String toString(){
//        return this.titulo;
//    }
//
//    public RealmList<IdSubtopico> getId_subtopicos() {
//        return subtopico;
//    }
//
//    public void setId_subtopico(RealmList<IdSubtopico> subtopicos) {
//        this.subtopico = subtopico;
//    }
//
//    public String getEndereco() {
//        return endereco;
//    }
//
//    public void setEndereco(String endereco) {
//        this.endereco = endereco;
//    }
//
//    public void setDia(String dia) {
//        this.dia = dia;
//    }
//
//    public String getDia() {
//        return dia;
//    }
//
//    public void setHorario(String horario) {
//        this.horario = horario;
//    }
//
//    public String getHorario() {
//        return horario;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//}