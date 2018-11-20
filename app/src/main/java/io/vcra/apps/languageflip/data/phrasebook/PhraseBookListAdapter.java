package io.vcra.apps.languageflip.data.phrasebook;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.vcra.apps.languageflip.R;

import java.util.Collections;
import java.util.List;


public class PhraseBookListAdapter extends RecyclerView.Adapter<PhraseBookListAdapter.PhraseBookViewHolder> {

    class PhraseBookViewHolder extends RecyclerView.ViewHolder { //A ViewHolder for a single PhraseBook
        private final TextView phraseBookItemView; //The text of the item

        private PhraseBookViewHolder(View itemView) {
            super(itemView);
            phraseBookItemView = itemView.findViewById(R.id.textView); //The title of the PhraseBook
        }
    }

    private final LayoutInflater inflater;
    private List<PhraseBook> mPhraseBooks = Collections.emptyList();

    public PhraseBookListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PhraseBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.phrase_book_list_item, parent, false);
        return new PhraseBookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhraseBookViewHolder holder, int position) {
        PhraseBook current = mPhraseBooks.get(position);
        holder.phraseBookItemView.setText(current.getPhrase());
    }

    public void setPhraseBooks(List<PhraseBook> phraseBooks) {
        mPhraseBooks = phraseBooks;
        notifyDataSetChanged();
    }

    public PhraseBook getFromPosition(int position){
        return mPhraseBooks.get(position);
    }

    public int getIDFromPosition(int position){
        return mPhraseBooks.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mPhraseBooks.size();
    }
}


