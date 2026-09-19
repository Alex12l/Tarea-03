package com.example.tarea03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatosBebidas extends RecyclerView.Adapter<AdapterDatosBebidas.ViewHolderDatos> {

    ArrayList<Bebida> listBebidas;
    private Context context;
    private OnAccionListener listener;

    public interface OnAccionListener {
        void onVer();
        void onEliminar(int id);
    }

    public AdapterDatosBebidas(ArrayList<Bebida> listEntrada) {
        this.listBebidas = listEntrada;
    }

    public AdapterDatosBebidas(Context context, ArrayList<Bebida> listEntrada, OnAccionListener listener) {
        this.context = context;
        this.listBebidas = listEntrada;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterDatosBebidas.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bebidas_list, parent, false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDatosBebidas.ViewHolderDatos holder, int position) {

        Bebida bebida = listBebidas.get(position);

        String nombre = bebida.getNombre();
        String tamanio = bebida.getTamanio();
        String precio = String.valueOf(bebida.getPrecio());
        String disponible = bebida.isDisponible() ? "Disponible" : "Agotado";

        holder.asignarDatos(nombre, tamanio, precio, disponible);

        holder.btnEliminar.setOnClickListener(v -> {

            if (listener != null) {
                listener.onEliminar(bebida.getId());
            }

        });
    }

    @Override
    public int getItemCount() {
        return listBebidas.size();
    }

    public static class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView txtNombreBebida, txtTamanio, txtPrecio, txtDisponible;
        Button btnEliminar;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            txtNombreBebida = itemView.findViewById(R.id.txtNombreBebida);
            txtTamanio = itemView.findViewById(R.id.txtTamanio);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtDisponible = itemView.findViewById(R.id.txtDisponible);

            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }

        public void asignarDatos(String nombre, String tamanio, String precio, String disponible) {

            txtNombreBebida.setText(nombre);
            txtTamanio.setText(tamanio);
            txtPrecio.setText("S/ " + precio);
            txtDisponible.setText(disponible);

        }
    }
}