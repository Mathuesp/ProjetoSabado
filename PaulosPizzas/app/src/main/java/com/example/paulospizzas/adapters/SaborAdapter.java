package com.example.paulospizzas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paulospizzas.R;

import java.util.ArrayList;

public class SaborAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> lista;

    public SaborAdapter(Context context, ArrayList<String> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).
                    inflate(R.layout.activity_item_lista_sabores,
                            viewGroup, false);
        }

        String sabor = lista.get(posicao);
        TextView tvSabor = view.findViewById(R.id.tvSabor);

        tvSabor.setText(sabor);
        return view;
    }
}
