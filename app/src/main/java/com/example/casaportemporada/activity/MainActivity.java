package com.example.casaportemporada.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casaportemporada.R;
import com.example.casaportemporada.activity.autenticacao.LoginActivity;
import com.example.casaportemporada.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {
    private ImageButton ib_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configCliques();
        iniciaComponentes();
    }

    public void configCliques() {
        findViewById(R.id.ib_menu).setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, ib_menu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_home, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getItemId() == R.id.menu_filtrar){
                    startActivity(new Intent(this, FiltrarAnunciosActivity.class));
                }else if(menuItem.getItemId() == R.id.menu_meus_anuncios){
                    if(FirebaseHelper.getAutenticado()){
                        startActivity(new Intent(this, MeusAnunciosActivity.class));
                    }else{
                        showDialogLogin();
                    }
                }else{
                    if(FirebaseHelper.getAutenticado()){
                        startActivity(new Intent(this, MinhaContaActivity.class));
                    }else{
                        showDialogLogin();
                    }
                }
                return true;
            });
            popupMenu.show();
        });
    }

    private void showDialogLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autenticação");
        builder.setMessage("Você não esta autenticado, deseja fazer isso agora?");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", (dialog, which) -> dialog.dismiss());//botao com texto não
        builder.setPositiveButton("Sim", (dialog, which) -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void iniciaComponentes(){
        ib_menu = findViewById(R.id.ib_menu);
    }
}