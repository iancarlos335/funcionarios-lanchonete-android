package com.lanchonete.ui.pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lanchonete.databinding.FragmentPedidosBinding;

public class PedidosFragment extends Fragment {

    private FragmentPedidosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PedidosViewModel slideshowViewModel =
                new ViewModelProvider(this).get(PedidosViewModel.class);

        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPedidos;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}