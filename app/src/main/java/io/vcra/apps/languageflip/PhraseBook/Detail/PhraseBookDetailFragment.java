package io.vcra.apps.languageflip.PhraseBook.Detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.vcra.apps.languageflip.R;
import io.vcra.apps.languageflip.data.phrase.Phrase;
import io.vcra.apps.languageflip.data.phrase.PhraseListAdapter;
import io.vcra.apps.languageflip.data.phrase.PhraseViewModel;

import java.util.List;

public class PhraseBookDetailFragment extends Fragment {

    private PhraseViewModel mViewModel;

    public static PhraseBookDetailFragment newInstance() {
        return new PhraseBookDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phrase_book_detail_list_fragment, container, false);
        beginViewModel();
        final RecyclerView recyclerView = view.findViewById(R.id.detail_recycler_view);
        final PhraseListAdapter adapter = new PhraseListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setupLiveData(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void setupLiveData(final PhraseListAdapter adapter) {
        mViewModel.getPhrases().observe(getViewLifecycleOwner(), new Observer<List<Phrase>>() {
            @Override
            public void onChanged(@Nullable List<Phrase> phrases) {
                adapter.setPhrases(phrases);
            }
        });
    }

    private void beginViewModel(){
        mViewModel = ViewModelProviders.of(this).get(PhraseViewModel.class);
        int id = getActivity().getIntent().getIntExtra("PhraseBook", -1);
        mViewModel.setPhraseBookID(id);
    }

}
