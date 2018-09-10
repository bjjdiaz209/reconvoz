package com.example.juan.reconvoz;


import java.util.ArrayList;
import java.util.Random;


public class Markov_Nodo {
    private ArrayList<String> siguientes;
    private String actual;

    public Markov_Nodo(String actual) {
        this.actual = actual;
        siguientes = new ArrayList<>();
    }

    public Markov_Nodo() {
        actual = "";
    }

    public void añadirSiguiente(String s){
        if(!s.equals(actual))
            siguientes.add(s);
    }

    public String obtenerUna(){
        Random r = new Random();
        return siguientes.get(r.nextInt(siguientes.size()));
    }

    public String getActual() {
        return actual;
    }
}