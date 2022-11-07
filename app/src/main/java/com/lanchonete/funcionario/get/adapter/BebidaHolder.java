package com.lanchonete.funcionario.get.adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lanchonete.R;
import com.lanchonete.model.Bebida;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BebidaHolder extends RecyclerView.ViewHolder {

    final TextView nome_bebida;
    final TextView descricao_bebida;
    final TextView valor_bebida;
    final ImageView imagem_bebida;

    Bebida bebida;

    public BebidaHolder(@NonNull View itemView) {
        super(itemView);

        new ImageDownloader().execute("https://funcionarios-lanchonete.vercel.app/" + bebida.getImagem());
        //TODO se ele conseguir pegar certinho, vai tudo funcionar perfeitamente. Se der erro, verificar em qual endereço foi configurado a API do springboot
        nome_bebida = itemView.findViewById(R.id.bebidasListItem_nome);
        descricao_bebida = itemView.findViewById(R.id.bebidasListItem_descricao);
        valor_bebida = itemView.findViewById(R.id.bebidasListItem_valor);
        imagem_bebida = itemView.findViewById(R.id.bebidasListItem_imagem);
    }



    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        HttpURLConnection httpURLConnection;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        public void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imagem_bebida.setImageBitmap(bitmap);
                Toast.makeText(imagem_bebida.getContext(), "Download deu certo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(imagem_bebida.getContext(), "Download deu erro!", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }




}

