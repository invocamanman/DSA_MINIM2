package eetac.dsa.activity;

public class Comanda {

    Producto producto;
    int cantidad;

    public Comanda(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                producto +
                ", cantidad=" + cantidad +
                '}';
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Comanda() {
    }
}
