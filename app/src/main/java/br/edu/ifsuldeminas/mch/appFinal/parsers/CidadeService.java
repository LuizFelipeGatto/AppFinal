package br.edu.ifsuldeminas.mch.appFinal.parsers;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.appFinal.domain.Cidade;

public class CidadeService extends AsyncTask<String, Integer, String> {

    private String serviceURL = "http://servicos.cptec.inpe.br/XML/listaCidades?city=city_name";
    private List<CidadeServiceObserver> observers = new ArrayList<CidadeServiceObserver>();

    @Override
    protected String doInBackground(String... strings) {
        // Uma cidade padrão se não receber nenhuma
        String city = "Sao Paulo";

        for(String attr : strings){
            city = attr;
        }

        serviceURL = serviceURL.replace("city_name", city);
        serviceURL = serviceURL.replace(" ", "%20");

        try {
            return HttpHelper.doGet(serviceURL);
        } catch (IOException e) {
            // TODO tratar adequadamente erro de rede
            return "";
        }
    }

    public boolean register (CidadeServiceObserver observer){

        if (observer != null){
            observers.add(observer);
            return true;
        }

        return false;
    }

    @Override
    protected void onPostExecute(String s) {
        List<Cidade> cities = CidadeParser.getCities(s);

        for(CidadeServiceObserver observer : observers){
            observer.serviceDone(cities);
        }
    }

}
