package com.example.tarea03;

public class Bebida {

    private int id;
    private String nombre;
    private String tamanio;
    private double precio;
    private boolean disponible;

    public Bebida() {
    }

    public Bebida(int id, String nombre, String tamanio, double precio, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.precio = precio;
        this.disponible = disponible;
    }

    public Bebida(String nombre, String tamanio, double precio, boolean disponible) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.precio = precio;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}