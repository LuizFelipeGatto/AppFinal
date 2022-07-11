package br.edu.ifsuldeminas.mch.appFinal.parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.appFinal.domain.Cidade;

public class CidadeParser {

    public static List<Cidade> getCities(String s) {
        List<Cidade> cities = new ArrayList<Cidade>();

        try{
            XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactory.newPullParser();
            InputStream is = new ByteArrayInputStream(s.getBytes("ISO-8859-1"));
            parser.setInput(is, null);

            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT)
            {
                if (event == XmlPullParser.START_TAG) {
                    Cidade c = new Cidade();

                    if (parser.getName().equals("cidade")){
                        event = parser.next();

                        while (!(event == XmlPullParser.END_TAG && parser.getName().equals("cidade"))){
                            if (event == XmlPullParser.START_TAG){
                                String tag = parser.getName();

                                event = parser.next();
                                if (event == XmlPullParser.TEXT){
                                    if (tag.equals("nome")){
                                        String name = new String(parser.getText().getBytes(), "UTF-8");
                                        c.setName(name);
                                    }

                                    if (tag.equals("uf"))
                                        c.setUf(parser.getText());

                                    if (tag.equals("id"))
                                        c.setId(Integer.parseInt(parser.getText()));
                                }
                            }

                            event = parser.next();
                        }

                        cities.add(c);
                    }
                }

                event = parser.next();
            }

        }catch (Exception e) { /* TODO: fazer algo se der erro de parser */ }
        finally {
            return cities;
        }
    }

}
