package com.example.diego.listatareas;

/**
 * Created by diego on 10.12.16.
 */

public class Constantes {

    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;
   /* *//**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     *//*
    private static final String PUERTO_HOST = "";*/
    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "dllopis.esy.es";
    /**
     * URLs del Web Service
     */

    public static final String GET = "http://" + IP +  "/I_Wish/obtener_metas.php";
    public static final String GET_BY_ID = "http://" + IP +  "/I_Wish/obtener_meta_por_id.php";
    public static final String UPDATE = "http://" + IP +  "/I_Wish/actualizar_meta.php";
    public static final String DELETE = "http://" + IP +  "/I_Wish/borrar_meta.php";
    public static final String INSERT = "http://" + IP +  "/I_Wish/insertar_meta.php";

    /**
     * Clave para el valor extra que representa al identificador de una meta
     */
    public static final String EXTRA_ID = "IDEXTRA";

}
