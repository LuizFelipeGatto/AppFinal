package br.edu.ifsuldeminas.mch.appFinal.services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Conexao {

    public static String getDados(String uri) throws JSONException {
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            StringBuffer stringBuffer = new StringBuffer();
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String linha;

            while((linha = bufferedReader.readLine()) != null){
                if(linha.contains("localidade")){
                    int l = linha.indexOf(":");
                    int f = linha.length();
                    linha = linha.substring(l,f).replace(":","").replace("\"","").replace(",","");
                    stringBuffer.append(linha+"\n");
                }
            }
            return stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
