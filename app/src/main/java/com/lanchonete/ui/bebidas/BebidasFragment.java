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

import com.lanchonete.FuncionarioMenu;
import com.lanchonete.databinding.FragmentBebidasBinding;
import com.lanchonete.funcionario.get.adapter.BebidaAdapter;
import com.lanchonete.funcionario.get.adapter.BebidaHolder;

public class BebidasFragment extends Fragment {

    private FragmentBebidasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BebidasViewModel bebidasViewModel =
                new ViewModelProvider(this).get(BebidasViewModel.class);

        binding = FragmentBebidasBinding.inflate(inflater, container, false); //Tenho q seguir a view indicada pelo Fragment, pq é derivado de outros nav, fragmentos e etc.

        View root = binding.getRoot();


        final TextView textView = binding.textBebidas; // é do tipo text view isso, se fosse Image é só mudar aqui, bem simples parece
        bebidasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}