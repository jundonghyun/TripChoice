package com.example.tripchoice.DongHyun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripchoice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShowRecommendAttractionAdapter extends RecyclerView.Adapter<ShowRecommendAttractionAdapter.ItemViewHolder> {
    private static Context context;
    private ArrayList<attraction> mdata = new ArrayList<>();
    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_list_item, parent, false);
        this.context = parent.getContext();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShowRecommendAttractionAdapter.ItemViewHolder holder, int position) {
        holder.onBind(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public void addItem(attraction data){
        mdata.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name, address;
        private ImageView imageView;
        private attraction data;

        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.attraction_name);
            address = itemView.findViewById(R.id.attraction_address);
            imageView = itemView.findViewById(R.id.attraction_imageView);
        }

        private void onBind(attraction data){
            this.data = data;

            name.setText(data.title);
            address.setText(data.address);
            imageView.setImageBitmap(data.bitmap);
        }
    }
}
