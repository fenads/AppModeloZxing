package br.com.grupoirmaosfranciosi.appmodelo.controller;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import br.com.grupoirmaosfranciosi.appmodelo.datasource.DataSource;
import br.com.grupoirmaosfranciosi.appmodelo.datamodel.EmblocamentoDataModel;
import br.com.grupoirmaosfranciosi.appmodelo.model.Emblocamento;

public class FardosController extends DataSource {

    ContentValues dados;

    public FardosController(Context context) {
        super(context);

    }

    public boolean salvar(Emblocamento obj){

        boolean sucesso;
        //boolean sucesso = true;

        dados = new ContentValues();

        dados.put(EmblocamentoDataModel.getCodBarraGs1(),obj.getCOD_BARRA_GS1());
        dados.put(EmblocamentoDataModel.getNumBloco(),obj.getNUM_BLOCO());
        dados.put(EmblocamentoDataModel.getNumFardo(),obj.getNUM_FARDO());
        sucesso = insert(EmblocamentoDataModel.getTABELA(),dados);

        return sucesso;
    }

    public boolean deletar(Emblocamento obj){

        boolean sucesso;
        //boolean sucesso = true;
        sucesso = deletar(EmblocamentoDataModel.getTABELA(),obj.getCOD_BARRA_GS1());

        return sucesso;
    }

    public boolean alterar(Emblocamento obj) {

        boolean sucesso;
        //boolean sucesso = true;

        dados = new ContentValues();

        dados.put(EmblocamentoDataModel.getCodBarraGs1(), obj.getCOD_BARRA_GS1());
        dados.put(EmblocamentoDataModel.getNumBloco(), obj.getNUM_BLOCO());
        dados.put(EmblocamentoDataModel.getNumFardo(), obj.getNUM_FARDO());

        sucesso = alterar(EmblocamentoDataModel.getTABELA(), dados);

        return sucesso;
    }


    public List<Emblocamento> listar(){

        return list(EmblocamentoDataModel.getTABELA());
    }

    public Emblocamento Buscar(Emblocamento obj){

        return getBlocoID(EmblocamentoDataModel.getTABELA(),obj);
    }

}
