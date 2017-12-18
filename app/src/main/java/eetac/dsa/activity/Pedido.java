package eetac.dsa.activity;

import java.util.Vector;

import eetac.dsa.model.UsuarioJSON;

public class Pedido {
    Vector<Comanda> comandas;
    UsuarioJSON usuario;

    public Pedido() {
    }

    public Pedido(UsuarioJSON usuario, Vector<Comanda> comandas) {
        this.comandas = comandas;
        this.usuario = usuario;
    }

    public Vector<Comanda> getComandas() {
        return comandas;
    }

    public void setComandas(Vector<Comanda> comandas) {
        this.comandas = comandas;
    }

    public UsuarioJSON getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioJSON usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        String return2 = "Pedido: ";
        int i = 1;
        for (Comanda c : comandas)
        {
            return2=return2+"\n "+i+" "+c.toString();
            i++;

        }
        return return2;
    }
}

