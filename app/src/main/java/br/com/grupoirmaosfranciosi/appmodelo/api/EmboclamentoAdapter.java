package br.com.grupoirmaosfranciosi.appmodelo.api;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import br.com.grupoirmaosfranciosi.appmodelo.R;
import br.com.grupoirmaosfranciosi.appmodelo.model.Emblocamento;

public class EmboclamentoAdapter extends RecyclerView.Adapter<EmboclamentoAdapter.ViewHolder> implements Filterable {

    public List<Emblocamento> mEmblocamentos;
    public List<Emblocamento> mEmblocamentosAll;
    public Context aContext;
    CardView conteiner;

    public EmboclamentoAdapter(List<Emblocamento> mEmblocamentos, Context aContext) {
        this.mEmblocamentos = mEmblocamentos;
        mEmblocamentosAll = new ArrayList<>(mEmblocamentos);
        this.aContext = aContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View linhaView = inflater.inflate(R.layout.linha_detalhe_blocos,parent,false);

        return new ViewHolder(linhaView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmboclamentoAdapter.ViewHolder holder, int position) {

        Emblocamento objDaLinha = mEmblocamentos.get(position);

        //Animação dos Cards
        conteiner.setAnimation(AnimationUtils.loadAnimation(aContext,R.anim.fade_scale));

        TextView rvCodigo = holder.rvCodigo;
        rvCodigo.setText(objDaLinha.getCOD_BARRA_GS1());

       TextView rvBloco = holder.rvBloco;
       rvBloco.setText(String.valueOf(objDaLinha.getNUM_BLOCO()));

    }

    @Override
    public int getItemCount() {
        return mEmblocamentos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView rvCodigo;
        public TextView rvBloco;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            conteiner = itemView.findViewById(R.id.cardViewDetalhe);
            rvCodigo = itemView.findViewById(R.id.rvCodigo);
            rvBloco = itemView.findViewById(R.id.rvBloco);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Emblocamento blocoSelecionado = mEmblocamentos.get(position);
            if(position != RecyclerView.NO_POSITION){
                Log.i(AppUtil.LOG_APP,"Bloco ID"+position+" Codigo : "+blocoSelecionado.getCOD_BARRA_GS1());
            }
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Emblocamento> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mEmblocamentosAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Emblocamento item : mEmblocamentosAll) {
                    if (item.getCOD_BARRA_GS1().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mEmblocamentos.clear();
            mEmblocamentos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}

