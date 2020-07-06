package br.com.grupoirmaosfranciosi.appmodelo.datamodel;

public class EmblocamentoDataModel {

    private static final String TABELA = "Fardos";
    private static final String COD_BARRA_GS1= "COD_BARRA_GS1";
    private static final String NUM_BLOCO= "NUM_BLOCO";
    private static final String NUM_FARDO= "NUM_FARDO";

    private static String queryCriarTabela = "";

    public static String criarTabela(){

        queryCriarTabela = "CREATE TABLE " + TABELA;
        queryCriarTabela += "( ";
        queryCriarTabela += COD_BARRA_GS1 + " TEXT, ";
        queryCriarTabela += NUM_BLOCO + " TEXT, ";
        queryCriarTabela += NUM_FARDO + " TEXT ";
        queryCriarTabela += ")";

        return queryCriarTabela;
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getCodBarraGs1() {
        return COD_BARRA_GS1;
    }

    public static String getNumBloco() {
        return NUM_BLOCO;
    }

    public static String getNumFardo() {
        return NUM_FARDO;
    }

    public static String getQueryCriarTabela() {
        return queryCriarTabela;
    }

    public static void setQueryCriarTabela(String queryCriarTabela) {
        EmblocamentoDataModel.queryCriarTabela = queryCriarTabela;
    }

}
