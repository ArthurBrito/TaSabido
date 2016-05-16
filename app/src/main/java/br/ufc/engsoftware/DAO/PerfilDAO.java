package br.ufc.engsoftware.DAO;

import java.util.Vector;

import br.ufc.engsoftware.models.Perfil;

public class PerfilDAO {
    private Vector<Perfil> perfis = new Vector<Perfil>();

    public void add(Perfil perfil) {
        if (perfis.contains(perfil)) {
            //throw perfil já existente
        }else{
            perfis.add(perfil);
        }
    }

    public void delete(Perfil perfil) {
        if(perfis.isEmpty()){
            // throw Tal perfil não existe
        }if(!perfis.contains(perfil)){
            // throw  tal perfil não existe
        } else {
            perfis.remove(perfil);
        }
    }

    public Vector<Perfil> update(Perfil perfil) {
        boolean itHas = false;
        for(Perfil it:perfis){
            if(it.equals(perfil)){
                itHas=true;
                it.setEmail(perfil.getEmail());
                it.setNome(perfil.getNome());
                it.setMoedas(perfil.getMoedas());
            }
        }
        if (!itHas) {
            //throw perfil não existe
        }
        return perfis;

    }

    public Vector<Perfil> list() {
        return perfis;
    }
}
