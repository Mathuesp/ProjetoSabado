package com.example.paulospizzas.model;

import java.util.ArrayList;

public class Pedido {
    private int id;
    private String tamanho;
    private ArrayList<String> sabores = new ArrayList<String>();
    private boolean comBorda = false;
    private boolean comRefri = false;
    private double vlPedido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public ArrayList<String> getSabores() {
        return sabores;
    }

    public void setSabores(ArrayList<String> sabores) {
        this.sabores = sabores;
    }

    public boolean isComBorda() {
        return comBorda;
    }

    public void setComBorda(boolean comBorda) {
        this.comBorda = comBorda;
    }

    public boolean isComRefri() {
        return comRefri;
    }

    public void setComRefri(boolean comRefri) {
        this.comRefri = comRefri;
    }

    public double getVlPedido() {
        return vlPedido;
    }

    public void setVlPedido(double vlPedido) {
        this.vlPedido = vlPedido;
    }
}
