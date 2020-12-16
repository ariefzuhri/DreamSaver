package com.ppab1.dreamsaver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.ppab1.dreamsaver.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ppab1.dreamsaver.data.ItemData;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ItemData> values;
    private LayoutInflater inflater;

    public ItemAdapter(Context context, ArrayList<ItemData> values){
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        final ItemData data = values.get(position);

        holder.totalText.setText(data.totalTarget);
        holder.dailyText.setText(data.dailyTarget);
        holder.savingText.setText(data.savingTarget);
        holder.dateText.setText(data.dateTarget);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Toast.makeText(context, "Anda memilih data " + data.totalTarget,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount(){
        return values.size();
    }


    public class ViewHolder  extends RecyclerView.ViewHolder {
        TextView dailyText;
        TextView dateText;
        TextView totalText;
        TextView savingText;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            dailyText=itemView.findViewById(R.id.tv_daily_target_target);
            dateText=itemView.findViewById(R.id.tv_date_target_target);
            totalText=itemView.findViewById(R.id.tv_total_savings_target);
            savingText=itemView.findViewById(R.id.tv_savings_target_target);
        }
    }
}
