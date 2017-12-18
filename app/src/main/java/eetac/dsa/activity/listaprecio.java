package eetac.dsa.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import eetac.dsa.R;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listaprecio extends AppCompatActivity {

    private ArrayAdapter<String> adaptador;
    private ProgressDialog progressDialog;
    private String BASE_URL;
    Button consultar;
    ArrayList<String> lista;
    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaprecio);

        BASE_URL = getString(R.string.URL_BASE);

        lista = new ArrayList<String>();

        adaptador= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        ListView lisv = (ListView) findViewById(R.id.Rankinglist);
        lisv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        int i;
        Getlista();

        lisv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String operacio = (String)adapterView.getItemAtPosition(i);//pendiente

            }
        });
    }

    public void Getlista()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");
        progressDialog.show();

        APIservice apiService = retrofit.create(APIservice.class);

        //JSON que enviamos al servido
        Call<ArrayList<Producto>> getlsita= apiService.listaPrecio();
        getlsita.enqueue(new Callback<ArrayList<Producto>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Producto>> login, Response<ArrayList<Producto>> response)
            {
                ArrayList<Producto> listaR = response.body();

                lista.clear();
                int i = 1;
                for (Producto u : listaR)
                {
                    lista.add(i+" "+u.toString());
                    i++;
                }

                adaptador.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Producto>> login, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

}
