package com.example.tripchoice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RcvActAdapter extends RecyclerView.Adapter<RcvActAdapter.ViewHolder> {

    private static Context context;
    private ArrayList<ActInfo> actInfo;
    private LayoutInflater mInflate;

    public RcvActAdapter(Context context, ArrayList<ActInfo> actInfo) {
        this.context = context;
        this.actInfo = actInfo;
        this.mInflate = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflate.inflate(R.layout.act_rcv, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.actImg.setImageBitmap(actInfo.get(position).getBitmap());
        holder.name.setText(actInfo.get(position).getName());
        holder.addr.setText(actInfo.get(position).getAddr());
    }

    @Override
    public int getItemCount() { return (null != actInfo ? actInfo.size() : 0); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ActInfo data;
        ImageView actImg;
        TextView name, addr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            actImg = itemView.findViewById(R.id.arcv_img);
            name = itemView.findViewById(R.id.arcv_name);
            addr = itemView.findViewById(R.id.arcv_addr);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        data = actInfo.get(pos);
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
