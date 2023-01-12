package com.example.aluraagenda.dao;

import com.example.aluraagenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorId = 1;

    public void salva(Aluno aluno){
        aluno.setIdAluno(contadorId);
        alunos.add(aluno);
        contadorId++;
    }

    public void edita(Aluno aluno){
        Aluno alunoEncontrado = buscaAlunoPeloId(aluno);

        if (alunoEncontrado != null){
            int posicaoDoAluno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoDoAluno, aluno);
        }
    }


    private Aluno buscaAlunoPeloId(Aluno aluno) {
        Aluno alunoEncontrado = null;
        for (Aluno aluno1 : alunos){
            if (aluno1.getIdAluno() == aluno.getIdAluno()){
                return aluno1;
            }
        }
        return null;
    }

    public List<Aluno> todos(){
        return  new ArrayList<>(alunos);
    }

    public void remove(Aluno aluno) {
        Aluno alunoDevolvido = buscaAlunoPeloId(aluno);
        if (alunoDevolvido != null){
            alunos.remove(alunoDevolvido);
        }

    }
}
