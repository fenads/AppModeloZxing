package br.com.grupoirmaosfranciosi.appmodelo.model;


public class Emblocamento {
    private String COD_BARRA_GS1;
    private int NUM_BLOCO;
    private int NUM_FARDO;

    public Emblocamento(){

    }


    public String getCOD_BARRA_GS1() {
        return COD_BARRA_GS1;
    }

    public void setCOD_BARRA_GS1(String COD_BARRA_GS1) {
        this.COD_BARRA_GS1 = COD_BARRA_GS1;
    }

    public Integer getNUM_BLOCO() {
        return NUM_BLOCO;
    }

    public void setNUM_BLOCO(Integer NUM_BLOCO) {
        this.NUM_BLOCO = NUM_BLOCO;
    }

    public Integer getNUM_FARDO() {
        return NUM_FARDO;
    }

    public void setNUM_FARDO(Integer NUM_FARDO) {
        this.NUM_FARDO = NUM_FARDO;
    }
}
