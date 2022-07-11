package br.edu.ifsuldeminas.mch.appFinal.parsers;

import java.util.List;

import br.edu.ifsuldeminas.mch.appFinal.domain.Cidade;

public interface CidadeServiceObserver {

    void serviceDone(List<Cidade> cities);
}
