package br.com.grupoirmaosfranciosi.appmodelo.datasource;

import android.content.ContentValues;
import android.content.Context;
import br.com.grupoirmaosfranciosi.appmodelo.datamodel.EmblocamentoDataModel;
import br.com.grupoirmaosfranciosi.appmodelo.model.Emblocamento;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class DataSource extends SQLiteOpenHelper {

    private static final String DB_NAME = "Fardos";
    private static final int DB_VERSION = 1;

    Cursor cursor;

    SQLiteDatabase db;

    public DataSource(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            db.execSQL(EmblocamentoDataModel.criarTabela());

        } catch (Exception e) {

            Log.e("Fardos", "DB---> ERRO: " + e.getMessage());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insert(String tabela, ContentValues dados) {

        boolean sucesso;
        //boolean sucesso = true;
        try {
            sucesso = db.insert(tabela, null,
                    dados) > 0;
        } catch (Exception e) {

            sucesso = false;
        }

        return sucesso;
    }

    public boolean deletar(String tabela, String COD_BARRA_GS1) {

        boolean sucesso;
        //boolean sucesso = true;

        sucesso = db.delete(tabela, "COD_BARRA_GS1=?",
                new String[]{COD_BARRA_GS1}) > 0;

        return sucesso;
    }

    public boolean alterar(String tabela, ContentValues dados) {

        boolean sucesso;
        //boolean sucesso = true;

        int id = dados.getAsInteger("id");

        sucesso = db.update(tabela, dados, "id=?",
                new String[]{Integer.toString(id)}) > 0;

        return sucesso;
    }

    public List<Emblocamento> getAllBlocos() {

        Emblocamento obj;

        // TIPADA
        List<Emblocamento> lista = new ArrayList<>();

        String sql = "SELECT * FROM " + EmblocamentoDataModel.getTABELA();

        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            do {

                obj = new Emblocamento();

                obj.setCOD_BARRA_GS1(cursor.getString(cursor.getColumnIndex(EmblocamentoDataModel.getCodBarraGs1())));
                obj.setNUM_FARDO(cursor.getInt(cursor.getColumnIndex(EmblocamentoDataModel.getNumFardo())));
                obj.setNUM_BLOCO(cursor.getInt(cursor.getColumnIndex(EmblocamentoDataModel.getNumBloco())));

                lista.add(obj);

            } while (cursor.moveToNext());

        }

        cursor.close();

        return lista;
    }


    public void deletarTabela(String tabela) {

        try {
            db.execSQL("DROP TABLE IF EXISTS " + tabela);
        }catch (Exception e){
            Log.e("DropTabela","Erro ao deletar tabela "+e.getMessage());
        }
    }

    public void criarTabela(String queryCriarTabela) {

        try {
            db.execSQL(queryCriarTabela);
        } catch (SQLiteCantOpenDatabaseException e) {
            Log.e("CriarTabela","Erro ao criar tabela "+e.getMessage());
        }
    }

    public Emblocamento getBlocoID(String tabela, Emblocamento obj){

        Emblocamento emblocamento = new Emblocamento();

        String sql = "Select * from "+tabela+" Where COD_BARRA_GS1 = '" + obj.getCOD_BARRA_GS1() + "'";
        try{
            cursor = db.rawQuery(sql,null);
            if(cursor.moveToNext()){

                emblocamento.setCOD_BARRA_GS1(cursor.getString(cursor.getColumnIndex(EmblocamentoDataModel.getCodBarraGs1())));
                emblocamento.setNUM_FARDO(cursor.getInt(cursor.getColumnIndex(EmblocamentoDataModel.getNumFardo())));
                emblocamento.setNUM_BLOCO(cursor.getInt(cursor.getColumnIndex(EmblocamentoDataModel.getNumBloco())));
            }
        }catch (SQLException e){
            Log.e("BuscarFardos","Erro Buscar Fardos "+e.getMessage());
        }

        return emblocamento;
    }
}
