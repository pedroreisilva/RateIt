package pt.ipg.rateit;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

public class AdaptadorFilmes extends RecyclerView.Adapter<AdaptadorFilmes.ViewHolderFilme> {
    private Cursor cursor;
    private Context context;

    public AdaptadorFilmes(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public AdaptadorFilmes.ViewHolderFilme onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_filme = LayoutInflater.from(context).inflate(R.layout.item_filme, parent, false);

        return new ViewHolderFilme(item_filme);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFilmes.ViewHolderFilme holder, int position) {
        cursor.moveToPosition(position);
        Filmes filme = Filmes.fromCursor(cursor);
        holder.setFilme(filme);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount();
    }

    public Filmes getFilmeSelecionado() {
        if (viewHolderFilmeSelecionado == null) return null;

        return viewHolderFilmeSelecionado.filme;
    }

    private static ViewHolderFilme viewHolderFilmeSelecionado = null;

    public class ViewHolderFilme extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewNome;
        private TextView textViewCategoria;
        private TextView textViewNota;
        private TextView textViewData;

        private Filmes filme;

        public ViewHolderFilme(@NonNull View itemView) {
            super(itemView);

            textViewNome = (TextView)itemView.findViewById(R.id.textViewNome);
            textViewCategoria =  (TextView)itemView.findViewById(R.id.textViewCategoria);
            textViewNota =  (TextView)itemView.findViewById(R.id.textViewNota);
            textViewData =  (TextView)itemView.findViewById(R.id.textViewData);

            itemView.setOnClickListener(this);

        }

        public void setFilme(Filmes filme) {
            this.filme = filme;

            textViewNome.setText(filme.getNome());
            textViewCategoria.setText(filme.getNomeCategoria());
            textViewNota.setText(filme.getNota());
            textViewData.setText(String.valueOf(filme.getData()));

        }

        @Override
        public void onClick(View v) {
            if (viewHolderFilmeSelecionado != null) {
                viewHolderFilmeSelecionado.desSeleciona();
            }

            viewHolderFilmeSelecionado = this;

            ((MainFilmes) context).atualizaOpcoesMenu();

            seleciona();
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorItemSelecionado);
        }
    }

}
