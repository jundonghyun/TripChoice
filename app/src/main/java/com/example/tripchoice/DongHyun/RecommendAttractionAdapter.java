package com.example.tripchoice.DongHyun;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripchoice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecommendAttractionAdapter extends RecyclerView.Adapter<RecommendAttractionAdapter.ItemViewHolder> {

    private static Context context;
    private ArrayList<RecommendAttractionData> mdata = new ArrayList<>();


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_attraction_item, parent, false);
        this.context = parent.getContext();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public void addItem(RecommendAttractionData data){
        mdata.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_name;
        private TextView textView_address;
        private RecommendAttractionData data;

        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            textView_name = itemView.findViewById(R.id.textView_name);
            textView_address = itemView.findViewById(R.id.textView_address);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        data = mdata.get(pos);
                        Intent intent = new Intent(context, RecommendAttractionActivity.class);
                        intent.putExtra("data", data);
                        v.getContext().startActivity(intent);
                    }
                }
            });

        }
        private void onBind(RecommendAttractionData data){
            this.data = data;

            textView_name.setText(data.POIName);
            textView_address.setText(data.POIAddress);
        }
    }
}