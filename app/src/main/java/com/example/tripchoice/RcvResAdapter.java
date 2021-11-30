package com.example.tripchoice;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

public class RcvResAdapter extends RecyclerView.Adapter<RcvResAdapter.ViewHolder> {

    private static Context context;
    private ArrayList<ResInfo> resInfo;
    private LayoutInflater mInflate;

    public RcvResAdapter(Context context, ArrayList<ResInfo> resInfo) {
        this.context = context;
        this.resInfo = resInfo;
        this.mInflate = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflate.inflate(R.layout.res_rcv, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        this.context = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(resInfo.get(position).getName());
        holder.menu.setText(resInfo.get(position).getMenu());
        holder.addr.setText(resInfo.get(position).getAddr());
        holder.resimg.setImageBitmap(resInfo.get(position).getBitmap());
    }

    @Override
    public int getItemCount() {
        return (null != resInfo ? resInfo.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ResInfo data;
        ImageView resimg;
        TextView name, menu, addr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            resimg = itemView.findViewById(R.id.rrcv_img);
            name =  itemView.findViewById(R.id.rrcv_name);
            menu = itemView.findViewById(R.id.rrcv_menu);
            addr = itemView.findViewById(R.id.rrcv_addr);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        data = resInfo.get(pos);

                        Intent intent = new Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+ data.getCall()));
                        context.startActivity(intent);

                    }
                }
            });
        }
    }
}
