package com.ppab1.dreamsaver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.data.ItemIntro;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder>{

    private List<ItemIntro> itemIntros;

    public IntroAdapter(List<ItemIntro> itemIntros) {
        this.itemIntros = itemIntros;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IntroViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_intro, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        holder.setIntroData(itemIntros.get(position));
    }

    @Override
    public int getItemCount() {
        return itemIntros.size();
    }

    class IntroViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageIntro;

        IntroViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageIntro = itemView.findViewById(R.id.imageIntro);
        }

        void setIntroData(ItemIntro itemIntro){
            textTitle.setText(itemIntro.getTitle());
            textDescription.setText(itemIntro.getDescription());
            imageIntro.setImageResource(itemIntro.getImage());
        }

    }
}
