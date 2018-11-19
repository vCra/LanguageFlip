package io.vcra.apps.languageflip.PhraseBook.Detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.vcra.apps.languageflip.R;

public class PhraseBookDetailFragment extends Fragment {

    private PhraseBookDetailViewModel mViewModel;

    public static PhraseBookDetailFragment newInstance() {
        return new PhraseBookDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.phrase_book_detail_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PhraseBookDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
