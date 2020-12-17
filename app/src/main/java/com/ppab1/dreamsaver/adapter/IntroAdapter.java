package com.ppab1.dreamsaver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.model.Intro;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder>{

    private List<Intro> intros;

    public IntroAdapter(List<Intro> intros) {
        this.intros = intros;
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
        holder.setIntroData(intros.get(position));
    }

    @Override
    public int getItemCount() {
        return intros.size();
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

        void setIntroData(Intro intro){
            textTitle.setText(intro.getTitle());
            textDescription.setText(intro.getDescription());
            imageIntro.setImageResource(intro.getImage());
        }

    }
}
