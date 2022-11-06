package com.lanchonete.ui.salgados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lanchonete.databinding.FragmentSalgadosBinding;

public class SalgadosFragment extends Fragment {

    private FragmentSalgadosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SalgadosViewModel salgadosViewModel =
                new ViewModelProvider(this).get(SalgadosViewModel.class);

        binding = FragmentSalgadosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSalgados;
        salgadosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}