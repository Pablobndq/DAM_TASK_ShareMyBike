package com.example.sharemybike.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sharemybike.R;

public class MainActivity extends AppCompatActivity {

    ImageButton imgBtnCp;
    ImageButton imgBtnEmail;
    EditText editTextCp;
    EditText editTextEmail;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.imgBtnCp = findViewById(R.id.image_btn_cp);
        this.imgBtnEmail = findViewById(R.id.image_btn_email);
        this.editTextCp = findViewById(R.id.edit_text_cp);
        this.editTextEmail = findViewById(R.id.edit_text_email);
        this.btnLogin = findViewById(R.id.btn_login);

        //Set Login Button action
        this.btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String cp = editTextCp.getText().toString();
                String email = editTextEmail.getText().toString();

                if(!cp.isEmpty() && !email.isEmpty() && isValidEmail(email) && isValidPostalCode(cp)){

                    Intent intentBikesActivity = new Intent(MainActivity.this, BikeActivity.class);
                    startActivity(intentBikesActivity);

                } else if (cp.isEmpty() && email.isEmpty()) {
                    editTextCp.setError("Código postal");
                    editTextEmail.setError("E-mail");
                    Toast.makeText(MainActivity.this, "Introduce el código postal y el e-mail", Toast.LENGTH_SHORT).show();
                } else if(cp.isEmpty() && !email.isEmpty()){
                    editTextCp.setError("Código postal");
                    Toast.makeText(MainActivity.this, "Introduce el código postal", Toast.LENGTH_SHORT).show();
                }else if(email.isEmpty() && !cp.isEmpty()){
                    editTextEmail.setError("E-mail");
                    Toast.makeText(MainActivity.this, "Introduce el e-mail", Toast.LENGTH_SHORT).show();
                }else if(!isValidEmail(email)){
                    editTextEmail.setError("E-mail");
                    Toast.makeText(MainActivity.this, "Introduce un e-mail válido", Toast.LENGTH_SHORT).show();
                }
            }


        });

        //Set CP Button action
        this.imgBtnCp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String cp = editTextCp.getText().toString();

                if(!cp.isEmpty() && isValidPostalCode(cp)){

                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + cp);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);

                }else{
                    Toast.makeText(MainActivity.this, "Introduce el código postal correcto para mostrar ubicación", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidPostalCode(String cp) {
        String cpRegex = "^[0-9]{5}$";
        return cp.matches(cpRegex);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}