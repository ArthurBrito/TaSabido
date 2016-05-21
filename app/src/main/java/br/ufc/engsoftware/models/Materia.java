    package br.ufc.engsoftware.models;

    import io.realm.RealmObject;
    import io.realm.annotations.RealmClass;

    /**
     * Created by Thiago on 14/05/2016.
     */
    @RealmClass
    public class Materia extends RealmObject{

        private int id_materia;
        private String nome;

        public Materia(){}

        public Materia(int id_materia, String nome) {
            this.id_materia = id_materia;
            this.nome = nome;
        }

        public int getId() {
            return id_materia;
        }

        public void setId(int id_materia) {
            this.id_materia = id_materia;
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
