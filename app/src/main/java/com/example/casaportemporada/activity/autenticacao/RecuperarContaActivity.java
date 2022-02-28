package com.example.casaportemporada.activity.autenticacao;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casaportemporada.R;
import com.example.casaportemporada.helper.FirebaseHelper;

import java.util.Objects;

public class RecuperarContaActivity extends AppCompatActivity {

    private EditText edit_email;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_conta);

        iniciaComponentes();
        configCliques();
    }

    public void validaDados(View view){
        String email = edit_email.getText().toString();
        if(!email.isEmpty()){
            progressBar.setVisibility(View.VISIBLE);
            recuperarSenha(email);
        }else{
            edit_email.requestFocus();
            edit_email.setError("Informe seu email.");
        }
    }

    private void recuperarSenha(String email){
        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this, "E-mail enviado com sucesso!", Toast.LENGTH_SHORT).show();
            }else{
                String erro = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    //habilita o toolbar voltar
    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    private void iniciaComponentes(){
        edit_email = findViewById(R.id.edit_emailRec);
        progressBar = findViewById(R.id.proressbar_rec);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText(R.string.recuperar_conta);
    }
}