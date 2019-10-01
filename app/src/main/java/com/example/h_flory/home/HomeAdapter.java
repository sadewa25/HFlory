package com.example.h_flory.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.h_flory.R;
import com.example.h_flory.model.DataModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewHolder> {

    private ArrayList<DataModel> dataItems;
    private Context context;

    public HomeAdapter(ArrayList<DataModel> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_komoditi,viewGroup,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.bindData(dataItems.get(i));
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        private TextView itemTitle, itemDetail;
        CircleImageView itemPhoto;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.tv_itemName);
            itemDetail = itemView.findViewById(R.id.tv_itemDetail);
            itemPhoto = itemView.findViewById(R.id.img_item_photo);

        }

        public void bindData(DataModel model){
            itemTitle.setText(model.getTitle());
            itemDetail.setText(model.getDetail());
            itemPhoto.setImageResource(model.getImage());
        }
    }

}
