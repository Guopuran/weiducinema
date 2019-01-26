package com.bw.movie.details.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseFullBottomSheetFragment;

public class DetailsFragment extends BaseFullBottomSheetFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setTopOffset(getResources().getDimensionPixelOffset(R.dimen.dp_120));
        return inflater.inflate(R.layout.frag_details, container, false);

    }

}
