package com.lanchonete.ui.doces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lanchonete.databinding.FragmentBebidasBinding;
import com.lanchonete.databinding.FragmentDocesBinding;
import com.lanchonete.ui.bebidas.BebidasViewModel;

public class DocesFragment extends Fragment {

    private FragmentDocesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocesViewModel docesViewModel =
                new ViewModelProvider(this).get(DocesViewModel.class);

        binding = FragmentDocesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDoces;
        docesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}