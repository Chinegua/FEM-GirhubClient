package com.example.chinegua.fem_githubclient;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    Cursor cursor;

    private static final String CONTENT_URI = "content://com.example.chinegua.apirest.provider/user";

    private static String[] PROJECTION = new String[] {
            "_id",
            "username",
            "avatar",
            "repos",
            "reposNumber",
            "follower",
            "following"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void filtrar(View v){
        TextView tvSearchUser = (TextView) findViewById(R.id.searchUser);
        String textoSearchUser = tvSearchUser.getText().toString();

        String recurso = CONTENT_URI + ((textoSearchUser.isEmpty()) ? "" : '/' + textoSearchUser );
        Uri uriContenido =  Uri.parse(recurso);

        ContentResolver contentResolver = getContentResolver();
        Log.i("MiW",uriContenido.toString());
        cursor = contentResolver.query(
                uriContenido,   // uri del recurso
                PROJECTION,     // Columnas a devolver
                null,           // Condición de la query
                null,           // Argumentos variables de la query
                null            // Orden de los resultados
        );

        cursor.moveToFirst();

        Log.i("MiW",cursor.getString(1));

        TextView name =(TextView) findViewById(R.id.tvName);
        name.setText(cursor.getString(1));

        ImageView image = (ImageView) findViewById(R.id.ivAvatar);
        Picasso.with(getApplicationContext()).load(cursor.getString(2)).into(image);

        TextView repos =(TextView) findViewById(R.id.repositorios);
        repos.setText(cursor.getString(4)+" Repositorios");

        TextView following =(TextView) findViewById(R.id.following);
        following.setText(" "+cursor.getString(6)+" Sig.");

        TextView follower =(TextView) findViewById(R.id.follower);
        follower.setText(" "+cursor.getString(5)+" Seg.");

        setOnViews();



    }

    public void setOnViews(){

        LinearLayout ly1 =(LinearLayout) findViewById(R.id.ly1);
        ly1.setVisibility(View.VISIBLE);

        View ly2 =(View) findViewById(R.id.ly2);
        ly2.setVisibility(View.VISIBLE);

        LinearLayout ly3 =(LinearLayout) findViewById(R.id.ly3);
        ly3.setVisibility(View.VISIBLE);

        LinearLayout ly4 =(LinearLayout) findViewById(R.id.ly4);
        ly4.setVisibility(View.VISIBLE);

    }

    public void goTo(View v){
        Uri uriUrl = Uri.parse("https://github.com/"+cursor.getString(1));
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);

    }
}
