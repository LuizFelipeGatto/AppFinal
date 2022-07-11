package br.edu.ifsuldeminas.mch.appFinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifsuldeminas.mch.appFinal.R;
import br.edu.ifsuldeminas.mch.appFinal.domain.Cidade;

public class CidadeAdapter extends RecyclerView.Adapter<CidadeAdapter.CityViewHolder> {

    private final List<Cidade> cities;
    private final Context context;

    private CityOnClickListener cityOnClickListener;

    public CidadeAdapter(Context context, List<Cidade> cities) {
        this.context = context;
        this.cities = cities;
//        this.cityOnClickListener = cityOnClickListener;
    }



    @Override
    public int getItemCount() {
        return this.cities != null ? this.cities.size() : 0;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_fragment, viewGroup, false);
        CityViewHolder holder = new CityViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CityViewHolder cityHolder, final int position) {

        Cidade city = cities.get(position);
        cityHolder.name.setText(city.getName());
        cityHolder.uf.setText(city.getUf());

        if (cityOnClickListener != null) {
            cityHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cityOnClickListener.onClickCity(cityHolder.itemView, position);
                }
            });
        }
    }

    public interface CityOnClickListener {
        void onClickCity(View view, int idx);
    }

    // ViewHolder com as views
    public static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView uf;
        CardView cardView;

        public CityViewHolder(View view) {
            super(view);

//            name = (TextView)view.findViewById(R.id.city_name);
//            uf = (TextView)view.findViewById(R.id.city_uf);
//            cardView = (CardView)view.findViewById(R.id.city_card_view);
        }
    }
}
