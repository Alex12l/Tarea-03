package com.example.tarea03;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaBebidasCafe extends AppCompatActivity implements AdapterDatosBebidas.OnAccionListener {

    ArrayList<Bebida> lstBebidas = new ArrayList<>();
    RecyclerView recyclerBebidas;
    AdapterDatosBebidas adapterDatosBebidas;
    RequestQueue requestQueue;

    private final String URL = "http://10.128.132.40:3000/productos";

    private int idBebida;

    private void loadUI() {
        recyclerBebidas = findViewById(R.id.recyclerBebidas);
    }

    private void obtenerDatosWS() {

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        lstBebidas.clear();

                        String nombre, tamanio;
                        double precio;
                        boolean disponible;
                        int id;

                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                id = jsonObject.getInt("id");
                                nombre = jsonObject.getString("nombre");
                                tamanio = jsonObject.getString("tamanio");
                                precio = jsonObject.getDouble("precio");
                                disponible = jsonObject.getInt("disponible") == 1;

                                lstBebidas.add(
                                        new Bebida(
                                                id,
                                                nombre,
                                                tamanio,
                                                precio,
                                                disponible
                                        )
                                );

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        adapterDatosBebidas.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error", volleyError.toString());
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_lista_bebidas_cafe);

        this.loadUI();

        adapterDatosBebidas = new AdapterDatosBebidas(
                this,
                lstBebidas,
                this
        );

        recyclerBebidas.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerBebidas.setAdapter(adapterDatosBebidas);

        this.obtenerDatosWS();
    }

    @Override
    public void onVer() {

        Toast.makeText(
                this,
                "Bebida seleccionada",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onEliminar(int id) {

        this.idBebida = id;

        this.validarAccion("eliminar");
    }

    private void validarAccion(String accion) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Cafeteria");

        builder.setMessage("¿Seguro de " + accion + "?");

        builder.setPositiveButton(
                "Si",
                (DialogInterface a, int b) -> {

                    if (accion.equalsIgnoreCase("eliminar"))
                        this.eliminarDatos();

                }
        );

        builder.setNegativeButton(
                "NO",
                null
        );

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void eliminarDatos() {

        requestQueue = Volley.newRequestQueue(this);

        String endPoint = URL + "/" + idBebida;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                endPoint,
                null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        String message = null;

                        try {

                            message = jsonObject.getString("message");

                            Toast.makeText(
                                    getApplicationContext(),
                                    message,
                                    Toast.LENGTH_SHORT
                            ).show();

                            obtenerDatosWS();

                        } catch (JSONException e) {

                            throw new RuntimeException(e);

                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        NetworkResponse response =
                                volleyError.networkResponse;

                        if (response != null && response.data != null) {

                            int statusCode = response.statusCode;

                            String errorJSON =
                                    new String(response.data);

                            validarError(statusCode, errorJSON);
                        }
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void validarError(int statusCode, String errorJSON) {

        Toast.makeText(
                this,
                "Error " + statusCode + ": " + errorJSON,
                Toast.LENGTH_LONG
        ).show();
    }
}