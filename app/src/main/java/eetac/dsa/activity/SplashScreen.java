package eetac.dsa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import eetac.dsa.R;
import eetac.dsa.model.KeyUser;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity
{
    private static final String TAG = Main.class.getSimpleName();
    private static Retrofit retrofit = null;
    private SharedPreferences sharedpref;
    private String BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        BASE_URL = getString(R.string.URL_BASE);

        //Inicia sesión automaticamente
        sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        if (!sharedpref.getString("username", "").equals("")) {
            IniciarSesion();
        }
        else
        {
            //Si no detecta un usuario logeado pasa al Main para iniciar sesion
            Intent intent = new Intent(this, Main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);

        Picasso.with(this)
                .load("https://cms-assets.tutsplus.com/uploads/users/21/posts/19431/featured_image/CodeFeature.jpg")
                .into(imageView);
        try { Thread.sleep(2000); }

        catch (InterruptedException ex) { android.util.Log.d("YourApplicationName", ex.toString()); }
    }
    public void IniciarSesion()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);

        //JSON que enviamos al servido
        final UsuarioJSON usuario = new UsuarioJSON(sharedpref.getString("username", ""), sharedpref.getString("password", ""));

        Call<KeyUser> login = apiService.login2(usuario);
        login.enqueue(new Callback<KeyUser>()
        {
            @Override
            public void onResponse(Call<KeyUser> login, Response<KeyUser> response)
            {
                int key = response.body().getKey();
                if(key == 0)
                {
                    Intent intent = new Intent(SplashScreen.this, Main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return;
                }

                //El usuario está autentificado
                usuario.setKey(key);
                Toast toast = Toast.makeText(getApplicationContext(), "Bienvenido  "+usuario.toString(), Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(SplashScreen.this, MainMenu.class);
                intent.putExtra("key", key);
                intent.putExtra("usuario", usuario);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<KeyUser> login, Throwable t)
            {
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}