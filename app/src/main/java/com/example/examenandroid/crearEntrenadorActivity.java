package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.examenandroid.entities.Entrenador;
import com.example.examenandroid.services.EntrenadorService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class crearEntrenadorActivity extends AppCompatActivity {

    ImageView imageView = findViewById(R.id.ivImagenA);
    EditText etImagen = findViewById(R.id.etImagen);
    String base64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_entrenador);
        Permisos();

        EditText etNombre = findViewById(R.id.etNombre);
        EditText etPueblo = findViewById(R.id.etPueblo);
        Button btnImagen = findViewById(R.id.btnSubir);
        Button btnCrear = findViewById(R.id.btnCrear);


        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String pueblo = etPueblo.getText().toString();
                String imagen = etImagen.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://upn.lumenes.tk/entrenador/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EntrenadorService service = retrofit.create(EntrenadorService.class);
                Entrenador entrenador = new Entrenador();
                entrenador.setNombres(nombre);
                entrenador.setPueblo(pueblo);
                entrenador.setImagen(imagen);
                Call<Entrenador> call = service.create(entrenador);
                call.enqueue(new Callback<Entrenador>() {
                    @Override
                    public void onResponse(Call<Entrenador> call, Response<Entrenador> response) {
                    }
                    @Override
                    public void onFailure(Call<Entrenador> call, Throwable t) {

                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String res = cursor.getString(column_index);
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(res);
            imageView.setImageBitmap(bitmap);
        }

        try {
            if (resultCode == RESULT_OK) {
                Uri path = data.getData();
                imageView.setImageURI(path);

                final InputStream imageStream = getContentResolver().openInputStream(path);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                base64 = convertBase64(selectedImage);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void Permisos() {
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10001);
        }
    }
    private String convertBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        base64 = Base64.encodeToString(b, Base64.DEFAULT);
        etImagen.setText(base64);
        return base64;
    }
}