package com.example.juan.reconvoz;

import java.util.ArrayList;


public class Markov {
    private ArrayList<Markov_Nodo> nodos;

    public Markov() {
        nodos = new ArrayList<>();
    }

    public void aprender(String s){
        s = limpiar(s);

        String[] palabras = s.split(" ");

        for(int i = 1; i<palabras.length-1;i++){
            procesar(palabras[i], palabras[i+1]);
        }

        procesar(palabras[palabras.length-1], ".");
    }

    public String ciclo(int palabras){
        return ciclo(palabras, ".");
    }

    public String ciclo(int palabras, String inicio){
        String tmp = "";
        String anterior = inicio;

        for(int i = 0;i<palabras;i++){
            String tmp2 = siguienteDe(anterior);
            tmp += " "+tmp2;
            anterior = tmp2;
        }

        return tmp;
    }

    private String siguienteDe(String s){
        Markov_Nodo tmp = buscar(s);
        return tmp.obtenerUna();
    }

    private void procesar(String s1, String s2){
        Markov_Nodo a = buscar(s1);
        if(a == null){
            Markov_Nodo tmp = new Markov_Nodo(s1);
            tmp.añadirSiguiente(s2);
            nodos.add(tmp);
        }else{
            a.añadirSiguiente(s2);
        }
    }

    private Markov_Nodo buscar(String s){
        for(Markov_Nodo n : nodos){
            if(n.getActual().equals(s))
                return n;
        }

        return null;
    }

    private String limpiar(String s){
        return s.toLowerCase()
                .replaceAll("\\."," .")
                .replaceAll("\\?"," ?")
                .replaceAll(","," ,")
                .replaceAll(";"," ;")
                .replaceAll("¿"," ¿")
                .replaceAll("!"," !")
                .replaceAll("¡"," ¡")
                .replaceAll("—","");
    }

}
