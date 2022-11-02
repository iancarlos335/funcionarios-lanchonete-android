package com.lanchonete.ui.funcionarios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lanchonete.databinding.FragmentFuncionariosBinding;

public class FuncionariosFragment extends Fragment {

    private FragmentFuncionariosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FuncionariosViewModel funcionariosViewModel =
                new ViewModelProvider(this).get(FuncionariosViewModel.class);

        binding = FragmentFuncionariosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFuncionarios;
        funcionariosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}