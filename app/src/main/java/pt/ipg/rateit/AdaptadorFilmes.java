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

public class AdaptadorFilmes extends RecyclerView.Adapter<AdaptadorFilmes.ViewHolderFilmes> {

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

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public AdaptadorFilmes.ViewHolderFilmes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_filme = LayoutInflater.from(context).inflate(R.layout.item_filme, parent, false);

        return new ViewHolderFilmes(item_filme);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AdaptadorFilmes.ViewHolderFilmes holder, int position) {
        cursor.moveToPosition(position);
        Filmes filme = Filmes.fromCursor(cursor);
        holder.setFilme(filme);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount();
    }


    public class ViewHolderFilmes extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewNome;
        private TextView textViewCategoria;
        private TextView textViewNota;
        private TextView textViewData;

        private Filmes filme;

        public ViewHolderFilmes(@NonNull View itemView) {
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
            textViewNota.setText(String.valueOf(filme.getNota()));
            textViewData.setText(String.valueOf(filme.getData()));

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, filme.getNome(), Toast.LENGTH_SHORT).show();

        }
    }

}
