package com.example.paulospizzas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulospizzas.adapters.SaborAdapter;
import com.example.paulospizzas.model.Pedido;
import com.example.paulospizzas.utils.Globais;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Pedido pedido = new Pedido();
    private RadioButton rdPequena;
    private RadioButton rdMedia;
    private RadioButton rdGrande;
    private Spinner spSabores;
    private ListView lvSabores;
    private Button btRemoverSabor;
    private CheckBox cbComBorda;
    private CheckBox cbComRefri;
    private TextView txSeuPedido;
    private TextView txVlPedido;
    private Button btFinalizaPedido;
    private Button btlimpaPedido;

    private String saborSelecionado;
    private int vlPizza = 0;
    private int qtdSabores = 0;
    private int qtdSelecionada = 0;
    private int vlBorda = 0;
    private int vlRefri = 0;
    private int itemSelecionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rdPequena = findViewById(R.id.rdPequena);
        rdMedia = findViewById(R.id.rdMedia);
        rdGrande = findViewById(R.id.rdGrande);
        spSabores = findViewById(R.id.spSabores);
        lvSabores = findViewById(R.id.lvSabores);
        btRemoverSabor = findViewById(R.id.btRemoverSabor);
        cbComBorda = findViewById(R.id.cbComBorda);
        cbComRefri = findViewById(R.id.cbComRefri);
        txSeuPedido = findViewById(R.id.txSeuPedido);
        txVlPedido = findViewById(R.id.txVlPedido);
        btFinalizaPedido = findViewById(R.id.btFinalizaPedido);
        btlimpaPedido = findViewById(R.id.btlimpaPedido);

        listaSabores();

        atualizaLista(Globais.listaSabores);

        spSabores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (qtdSabores == 0){
                    Toast.makeText(MainActivity.this,"Para que possa selecionar um sabor é necessário escolher o tamanho da pizza!", Toast.LENGTH_LONG).show();
                } else if (qtdSelecionada < qtdSabores && !((String) spSabores.getItemAtPosition(position)).equals("")) {
                    qtdSelecionada+=1;
                    saborSelecionado = (String) spSabores.getItemAtPosition(position);
                    adicionaSabor(saborSelecionado);
                    atualizaPedido();
                } else if (qtdSelecionada == qtdSabores){
                    Toast.makeText(MainActivity.this,"O limite de sabores, para o tamanho de pizza escolhido, foi atingido!", Toast.LENGTH_LONG).show();
                }

                spSabores.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lvSabores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelecionado = position;
            }
        });

        btlimpaPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpaPedido();
            }
        });
    }

    private void adicionaSabor(String sabor){
        if (!sabor.trim().equals("")){
            Globais.listaSabores.add(sabor);
            atualizaLista(Globais.listaSabores);
        }
    }

    private void listaSabores(){
        if (Globais.listaSabores == null){
            Globais.listaSabores = new ArrayList<>();
        }

        String[] vetorSabores = new String[]{"", "Calabresa", "Catufile", "Lombo Catupiry",
                                                 "Baiana", "Dois Amores", "Brigadeiro"};

        ArrayAdapter adapterSpSabores = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, vetorSabores);

        spSabores.setAdapter(adapterSpSabores);
    }

    private void atualizaLista(ArrayList<String> lista){
        SaborAdapter adapterLvSabores = new SaborAdapter(this, lista);
        lvSabores.setAdapter(adapterLvSabores);
    }

    private void atualizaPedido(){
        if (!pedido.getTamanho().trim().equals("")) {
            String mensagem = "Pizza ";

            mensagem += pedido.getTamanho() + (qtdSabores == 1 ? " com o sabor: \n" : " com os sabores: \n");

            for (String sabor : Globais.listaSabores) {
                mensagem += "- " + sabor + "\n";
            }

            if (pedido.isComBorda()) {
                mensagem += "Com borda e ";
            } else {
                mensagem += "Sem borda e ";
            }

            if (pedido.isComRefri()) {
                mensagem += "com refrigerante";
            } else {
                mensagem += "sem refrigerante";
            }

            txSeuPedido.setText(mensagem);

            pedido.setVlPedido(vlPizza + vlBorda + vlRefri);

            txVlPedido.setText(String.valueOf(pedido.getVlPedido()));
        }
    }

    public void verificaTamanhoPizza(View view) {
        if (view.getId() == R.id.rdPequena){
            if (qtdSelecionada > 1){
                Toast.makeText(MainActivity.this, "É necessário remover sabores para que seja possível alterar o tamanho da pizza!", Toast.LENGTH_LONG).show();
                if (qtdSabores == 2){
                    rdMedia.setChecked(true);
                } else if (qtdSabores == 4) {
                    rdGrande.setChecked(true);
                }
            } else {
                pedido.setTamanho("Pequena");
                vlPizza = 20;
                qtdSabores = 1;
            }
        }else if (view.getId() == R.id.rdMedia) {
            if (qtdSelecionada > 2){
                Toast.makeText(MainActivity.this, "É necessário remover sabores para que seja possível alterar o tamanho da pizza!", Toast.LENGTH_LONG).show();
                if (qtdSabores == 4) {
                    rdGrande.setChecked(true);
                }
            } else {
                pedido.setTamanho("Média");
                vlPizza = 30;
                qtdSabores = 2;
            }
        } else if (view.getId() == R.id.rdGrande) {
            pedido.setTamanho("Grande");
            vlPizza = 40;
            qtdSabores = 4;
        }

        Toast.makeText(MainActivity.this, "Você poderá selecionar " + String.valueOf(qtdSabores) + " sabores!", Toast.LENGTH_LONG).show();

        atualizaPedido();
    }

    public void verificaBorda(View view){
        if (cbComBorda.isChecked()){
            pedido.setComBorda(true);
            vlBorda = 10;
        } else {
            pedido.setComBorda(false);
            vlBorda = 0;
        }
        atualizaPedido();
    }

    public void verificaRefri(View view){
        if (cbComRefri.isChecked()){
            pedido.setComRefri(true);
            vlRefri = 5;
        } else {
            pedido.setComRefri(false);
            vlRefri = 0;
        }
        atualizaPedido();
    }

    public void removeItem(View view) {
        if (itemSelecionado != -1){
            Globais.listaSabores.remove(itemSelecionado);
            atualizaLista(Globais.listaSabores);
        }

        itemSelecionado = -1;
        qtdSelecionada -= 1;
        atualizaPedido();

    }

    public void finalizaPedido(View view) {
        if (Globais.listaSabores.size() > 0) {
            Toast.makeText(MainActivity.this, txSeuPedido.getText().toString(), Toast.LENGTH_LONG).show();
            limpaPedido();
        } else {
            Toast.makeText(MainActivity.this, "Para concluir o pedido, é necessário selecionar ao menos um sabor!", Toast.LENGTH_LONG).show();
        }
    }

    public void limpaPedido(){
        Globais.listaSabores.clear();
        atualizaLista(Globais.listaSabores);

        cbComRefri.setChecked(false);
        cbComBorda.setChecked(false);
        txSeuPedido.setText("");
        txVlPedido.setText("");
        spSabores.setSelection(0);

        qtdSelecionada = 0;
        vlBorda = 0;
        vlRefri = 0;
        itemSelecionado = -1;

        pedido = new Pedido();
    }
}