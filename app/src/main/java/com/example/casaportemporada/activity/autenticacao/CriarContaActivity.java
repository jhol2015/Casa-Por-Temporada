package com.example.casaportemporada.activity.autenticacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casaportemporada.R;
import com.example.casaportemporada.activity.MainActivity;
import com.example.casaportemporada.databinding.ActivityCriarContaBinding;
import com.example.casaportemporada.helper.FirebaseHelper;
import com.example.casaportemporada.model.Usuario;

import java.util.Objects;

public class CriarContaActivity extends AppCompatActivity {

    //Declara os EditTexts criados no xml
    private EditText edit_nome;
    private EditText edit_email;
    private EditText edit_telefone;
    private EditText edit_senha;
    private ProgressBar progressBar;

    private ActivityCriarContaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarContaBinding.inflate(getLayoutInflater());
        setContentView (binding.getRoot());

        configCliques();
        iniciaComponentes();

    }

    //metodo de recuperar os dados passados
    public void validaDados(View view){
        String nome = edit_nome.getText().toString();
        String email = edit_email.getText().toString();
        String telefone = edit_telefone.getText().toString();
        String senha = edit_senha.getText().toString();

        //valida os dados recebidos.
        if(!nome.isEmpty()){
            if(!email.isEmpty()){
                if(!telefone.isEmpty()){
                    if(!senha.isEmpty()){
                        progressBar.setVisibility(View.VISIBLE);

                        Usuario usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setTelefone(telefone);
                        usuario.setSenha(senha);

                        cadastrarUsuario(usuario);
                    }else{
                        edit_senha.requestFocus();
                        edit_senha.setError("Informe sua senha.");
                    }

                }else{
                    edit_telefone.requestFocus();
                    edit_telefone.setError("Informe seu telefone.");
                }

            }else{
                edit_email.requestFocus();
                edit_email.setError("Informe seu email");
            }

        }else{
            edit_nome.requestFocus();
            edit_nome.setError("Informe seu nome.");
        }
    }

    private void cadastrarUsuario(Usuario usuario){
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String idUser = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                usuario.setId(idUser);
                usuario.salvar();//so salva usuario se tiver sucesso

                //encerra ela de cadastro
                finish();

                //abre proxima tela
                startActivity(new Intent(this, MainActivity.class));
            }else{
                String error = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    private void iniciaComponentes(){
        //iniciar os editsTexts passando para o findViewById
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_senha = findViewById(R.id.edit_senha);
        progressBar = findViewById(R.id.progress_circular);
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText(R.string.crie_sua_conta);
    }


}