package com.example.h_flory.alat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.h_flory.DetailActivity;
import com.example.h_flory.R;
import com.example.h_flory.model.DataModel;

import java.util.ArrayList;

public class AlatAdapter extends RecyclerView.Adapter<AlatAdapter.viewHolder> {

    private ArrayList<DataModel> dataItems;
    private Context context;

    public AlatAdapter(ArrayList<DataModel> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.bindData(dataItems.get(i),context);
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        private TextView itemTitle, itemTime;
        private Button btnPower;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.tv_itemName);
            itemTime = itemView.findViewById(R.id.tv_itemTime);
            btnPower = itemView.findViewById(R.id.btn_power);
        }

        public void bindData(final DataModel model, final Context context){
            itemTitle.setText(model.getTitle());
            itemTime.setText(model.getDetail());

            if (model.getStatus() == 1){
                btnPower.setText("Off");
                btnPower.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            }

            btnPower.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.getStatus() == 0){
                        context.startActivity(new Intent(context, DetailActivity.class));
                    }
                }
            });
        }
    }

}