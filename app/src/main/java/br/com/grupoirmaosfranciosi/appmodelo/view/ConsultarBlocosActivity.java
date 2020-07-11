package br.com.grupoirmaosfranciosi.appmodelo.view;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import java.util.List;
import br.com.grupoirmaosfranciosi.appmodelo.R;
import br.com.grupoirmaosfranciosi.appmodelo.api.EmboclamentoAdapter;
import br.com.grupoirmaosfranciosi.appmodelo.controller.FardosController;
import br.com.grupoirmaosfranciosi.appmodelo.model.Emblocamento;

public class ConsultarBlocosActivity extends AppCompatActivity {

    List<Emblocamento> emblocamentos;
    EmboclamentoAdapter adapter;
    FardosController controller;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consultar_blocos);
        recyclerView = findViewById(R.id.rvBlocosView);
        controller = new FardosController(getApplicationContext());

        emblocamentos = controller.listar();

        adapter = new EmboclamentoAdapter(emblocamentos,getApplicationContext());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buscar, menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}