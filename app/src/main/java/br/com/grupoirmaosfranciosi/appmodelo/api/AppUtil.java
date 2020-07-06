package br.com.grupoirmaosfranciosi.appmodelo.api;

import java.util.Calendar;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;



public class AppUtil {

    public static final int TIME_SPLASH = 5 * 1000;
    public static final String URL_WEB_SERVICE = "http://192.168.0.108/api/Bloco/Recuperar";
    public static final int CONNECTION_TIMEOUT = 30000; // 30 segundos
    public static final int READ_TIMEOUT = 30000; // 30 segundos

    public static String getDataAtual(){
        String dataAtual = "00/00/0000";
        String dia,mes,ano;
        try{
            Calendar calendario = Calendar.getInstance();
            dia = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
            mes = String.valueOf(calendario.get(Calendar.MONTH)+1);
            ano = String.valueOf(calendario.get(Calendar.YEAR));

            dia = calendario.get(Calendar.DAY_OF_MONTH) < 10 ? "0"+dia : dia;

            int mesAtual = (Calendar.MONTH)+1;

            mes = (mesAtual < 10) ? "0"+mes : mes;

            dataAtual = dia+"/"+mes+"/"+ano;


        }catch (Exception e){
            Log.e("Data","Erro ao alterar data "+e.getMessage());
        }

        return dataAtual;
    }

    public static String getHoraAtual(){
        String horaAtual = "00:00:00";
        String hora,minuto,segundo;

        try{
            Calendar calendario = Calendar.getInstance();
            int iHora = calendario.get(Calendar.HOUR_OF_DAY);
            int iMinuto = calendario.get(Calendar.MINUTE);
            int iSegundo = calendario.get(Calendar.SECOND);

            hora = (iHora <=9) ? "0"+iHora : Integer.toString(iHora);
            minuto = (iMinuto <=9) ? "0"+iMinuto : Integer.toString(iMinuto);
            segundo = (iSegundo <=9) ? "0"+iSegundo : Integer.toString(iSegundo);

            horaAtual = hora+":"+minuto+":"+segundo;

        }
        catch (Exception e){
            Log.e("Hora","Erro ao alterar hora "+e.getMessage());
        }

        return horaAtual;

    }

    public static void showMensagem(Context context, String mensagem){

        Toast.makeText(context,mensagem,Toast.LENGTH_LONG).show();
    }

}
