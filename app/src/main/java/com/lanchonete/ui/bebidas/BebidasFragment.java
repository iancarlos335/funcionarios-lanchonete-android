package com.lanchonete.ui.bebidas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.databinding.FragmentBebidasBinding;

public class BebidasFragment extends Fragment {

    private FragmentBebidasBinding binding;

    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BebidasViewModel bebidasViewModel =
                new ViewModelProvider(this).get(BebidasViewModel.class);

        binding = FragmentBebidasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textBebidas;
        bebidasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}