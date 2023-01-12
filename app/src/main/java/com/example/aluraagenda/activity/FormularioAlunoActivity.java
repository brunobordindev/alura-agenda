package com.example.aluraagenda.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.aluraagenda.R;
import com.example.aluraagenda.model.Aluno;
import com.example.aluraagenda.dao.AlunoDAO;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String CHAVE_ALUN0 = "aluno";
    private Button btnSalvar;
     private EditText campoNome, campoTelefone, campoEmail;
     private final AlunoDAO dao = new AlunoDAO();
     private Aluno aluno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        setTitle("Novo Aluno");

        campoNome = findViewById(R.id.editNome);
        campoTelefone = findViewById(R.id.ediTelefone);
        campoEmail = findViewById(R.id.editEmail);



        //retornar a informacao do aluno do MainActivity para FormularioAlunoActivity e poder Editar os campos
        carregaAluno();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_aluno, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.formulario_aluno_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUN0)){
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUN0);
            setTitle("Edita Aluno");
            preencheCampos();
        }else{
            setTitle("Novo Aluno");
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
    }


    private void finalizaFormulario() {
        preencherAluno();
        if (aluno.idValido()){
            dao.edita(aluno);
        }else {
            dao.salva(aluno);
        }
        finish();
    }


    //troquei private Aluno criaAluno() para private void preencherAluno()
    private void preencherAluno(){
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();


        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }
}