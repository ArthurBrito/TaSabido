package br.ufc.engsoftware.DAO;

import java.util.Vector;

import br.ufc.engsoftware.models.Materia;

/**
 * Created by Thiago on 14/05/2016.
 */
public class MateriaDAO {

    private Vector<Materia> materias;

    public MateriaDAO() {
        materias = new Vector<Materia>();

        materias.add(new Materia(1, "Calculo 1"));
        materias.add(new Materia(2, "Algebra Linear"));
        materias.add(new Materia(3, "Engenharia de Software"));
        materias.add(new Materia(4, "Calculo 2"));
        materias.add(new Materia(5, "Fundamentos de Programação"));
        materias.add(new Materia(6, "Programação"));
        materias.add(new Materia(7, "Tecnicas de Programação"));
        materias.add(new Materia(8, "Transmissão de Dados"));
        materias.add(new Materia(9, "Arquitetura de Software"));
    }

    public Vector<Materia> list(){
        Vector<Materia> vector = (Vector<Materia>) materias.clone();
        return vector;
    }
}
