package com.example.aluraagenda.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.aluraagenda.R;
import com.example.aluraagenda.adapter.ListaAlunosAdapter;
import com.example.aluraagenda.model.Aluno;
import com.example.aluraagenda.dao.AlunoDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.aluraagenda.activity.ConstantesActivities.CHAVE_ALUNO;

public class MainActivity extends AppCompatActivity {



    FloatingActionButton btnCadastrarAluno;
    private final AlunoDAO dao = new AlunoDAO();
    private ListaAlunosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Lista de Alunos");

        btnCadastrarAluno = findViewById(R.id.fab_novo_aluno);


        configuraFabNovoAluno();
        configuraLista();




    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.remover){
            confirmaRemocao(item);

        }

        return super.onContextItemSelected(item);
    }

    private void confirmaRemocao( MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Remover aluno")
                .setMessage("Tem certeza que deseja remover o aluno?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Aluno alunoEscolhido = (Aluno) adapter.getItem(menuInfo.position);
                        remove(alunoEscolhido);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void configuraFabNovoAluno(){
        btnCadastrarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               abreFormularioModoItemAluno();
            }
        });
    }

    public void abreFormularioModoItemAluno(){
        startActivity(new Intent(MainActivity.this, FormularioAlunoActivity.class));
    }

    @Override

    protected void onResume() {
        super.onResume();
        atualizaAlunos();


    }

    private void atualizaAlunos() {
        adapter.atualiza(dao.todos());

    }

    public void configuraLista(){
        ListView listaAlunos = findViewById(R.id.listaAlunos);
        configuraAdapter(listaAlunos);
        configuraListenerDeCliquePorItem(listaAlunos);

        registerForContextMenu(listaAlunos);
    }



    private void remove(Aluno aluno) {
        dao.remove(aluno);
        adapter.remove(aluno);
    }

    private void configuraListenerDeCliquePorItem(ListView listaAlunos) {
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(i);
                abreFormularioModoEditaAluno(alunoEscolhido);
            }

            private void abreFormularioModoEditaAluno(Aluno aluno) {
                Intent intentt = new Intent(MainActivity.this, FormularioAlunoActivity.class);
                intentt.putExtra(CHAVE_ALUNO, aluno);
                startActivity(intentt);
            }
        });
    }

    private void configuraAdapter(ListView listaAlunos) {

        adapter = new ListaAlunosAdapter(this);
        listaAlunos.setAdapter(adapter);
    }
}