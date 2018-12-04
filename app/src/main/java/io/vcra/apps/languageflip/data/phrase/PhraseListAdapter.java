package io.vcra.apps.languageflip.data.phrase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.vcra.apps.languageflip.R;

import java.util.Collections;
import java.util.List;

public class PhraseListAdapter extends RecyclerView.Adapter<PhraseListAdapter.PhraseViewHolder> {

    private final LayoutInflater inflater;
    private List<Phrase> phrases = Collections.emptyList();
    public PhraseListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PhraseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = inflater.inflate(R.layout.phrase_book_detail_list_item, viewGroup, false);
        return new PhraseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhraseViewHolder phraseViewHolder, int position) {
        Phrase current = phrases.get(position);
        phraseViewHolder.lang1Text.setText(current.getPrimaryWord());
        phraseViewHolder.lang2Text.setText(current.getSecondaryWord());
    }

    @Override
    public int getItemCount() {
        return phrases.size();
    }

    public Phrase getFromPosition(int position) {
        return phrases.get(position);
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
        notifyDataSetChanged();

    }

    class PhraseViewHolder extends RecyclerView.ViewHolder {
        private final TextView lang1Text;
        private final TextView lang2Text;


        private PhraseViewHolder(@NonNull View itemView) {
            super(itemView);
            lang1Text = itemView.findViewById(R.id.lang1TextView);
            lang2Text = itemView.findViewById(R.id.lang2TextView);
        }
    }
}
