package com.example.diego.listatareas.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.diego.listatareas.Constantes;
import com.example.diego.listatareas.Meta;
import com.example.diego.listatareas.R;
import com.example.diego.listatareas.VolleySingleton;
import com.example.diego.listatareas.actividades.UpdateActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by diego on 10.12.16.
 */

public class DetailFragment extends Fragment {

    /**
     * Etiqueta de depuración
     */
    private static final String TAG = DetailFragment.class.getSimpleName();

    /*
    Instancias de Views
     */
    private AppBarLayout cabecera;
    private TextView titulo;
    private TextView descripcion;
    private TextView prioridad;
    private TextView fechaLim;
    private TextView categoria;
    private ImageButton editButton;
    private String extra;
    private Gson gson = new Gson();

    public DetailFragment() {
    }

    public static DetailFragment createInstance(String idMeta) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.EXTRA_ID, idMeta);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        // Obtención de views
        cabecera = (AppBarLayout) v.findViewById(R.id.cabecera);
        titulo = (TextView) v.findViewById(R.id.titulo);
        descripcion = (TextView) v.findViewById(R.id.descripcion);
        prioridad = (TextView) v.findViewById(R.id.prioridad);
        fechaLim = (TextView) v.findViewById(R.id.fecha);
        categoria = (TextView) v.findViewById(R.id.categoria);
        editButton = (ImageButton) v.findViewById(R.id.fab);

        // Obtener extra del intent de envío
        extra = getArguments().getString(Constantes.EXTRA_ID);

        // Setear escucha para el fab
        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de actualización
                        Intent i = new Intent(getActivity(), UpdateActivity.class);
                        i.putExtra(Constantes.EXTRA_ID, extra);
                        getActivity().startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION);
                    }
                }
        );

        // Cargar datos desde el web service
        cargarDatos();

        return v;
    }

    /**
     * Obtiene los datos desde el servidor
     */
    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GET_BY_ID + "?idMeta=" + extra;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }

    /**
     * Procesa cada uno de los estados posibles de la
     * respuesta enviada desde el servidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "mensaje"
            String mensaje = response.getString("estado");

            switch (mensaje) {
                case "1":
                    // Obtener objeto "meta"
                    JSONObject object = response.getJSONObject("meta");

                    //Parsear objeto
                    Meta meta = gson.fromJson(object.toString(), Meta.class);

                    // Asignar color del fondo
                    switch (meta.getCategoria()) {
                        case "Salud":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.saludColor));
                            break;
                        case "Finanzas":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.finanzasColor));
                            break;
                        case "Ocio":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.ocioColor));
                            break;
                        case "Profesional":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.profesionalColor));
                            break;
                        case "Material":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.materialColor));
                            break;
                    }

                    // Seteando valores en los views
                    titulo.setText(meta.getTitulo());
                    descripcion.setText(meta.getDescripcion());
                    switch (meta.getPrioridad()) {
                        case "Alta":
                            prioridad.setText(R.string.Alta);
                            break;
                        case "Media":
                            prioridad.setText(R.string.Media);
                            break;
                        case "Baja":
                            prioridad.setText(R.string.Baja);
                            break;
                    }

                    fechaLim.setText(meta.getFechaLim());

                    switch (meta.getCategoria()) {
                        case "Salud":
                            categoria.setText(R.string.Salud);
                            break;
                        case "Finanzas":
                            categoria.setText(R.string.Finanzas);
                            break;
                        case "Ocio":
                            categoria.setText(R.string.Ocio);
                            break;
                        case "Profesional":
                            categoria.setText(R.string.Profesional);
                            break;
                        case "Material":
                            categoria.setText(R.string.Material);
                            break;
                    }


                    break;

                case "2":
                    Toast.makeText(getActivity(), R.string.noData,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    Toast.makeText(getActivity(), R.string.noID,
                            Toast.LENGTH_LONG).show();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

