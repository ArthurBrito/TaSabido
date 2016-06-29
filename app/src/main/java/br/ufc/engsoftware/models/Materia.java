    package br.ufc.engsoftware.models;

    import io.realm.RealmObject;
    import io.realm.annotations.RealmClass;

    /**
     * Created by Thiago on 14/05/2016.
     */
    @RealmClass
    public class Materia extends RealmObject{

        private int id;
        private String nome;

        public Materia(){}

        public Materia(int id_materia, String nome) {
            this.id = id_materia;
            this.nome = nome;
        }

        public int getId_materia() {
            return id;
        }

        public void setId_materia(int id_materia) {
            this.id = id_materia;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String toString(){
            return this.nome;
        }
    }
