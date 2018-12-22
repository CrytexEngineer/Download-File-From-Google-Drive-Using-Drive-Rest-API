package com.example.aqil.hommy2;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aqil.hommy2.Entity.TrashEntitiy;

import java.util.ArrayList;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.ViewHolder> {

    public final static String EXTRA_MOVIE = "movie";
    private ArrayList<TrashEntitiy> listTrashEntity = new ArrayList<>();
    private static final int REQUEST_DETAIL = 1;
    public static String EXTRA_REQUEST_CODE = "request code";
    private int position;

    public rvAdapter(Context context) {
        this.context = context;
    }

    private Context context;
    Intent intent;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_layout, parent, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Log.d("TAG", "onBindViewHolder: ");


        Glide.with(context).load(listTrashEntity.get(position).imagePath).apply(new RequestOptions().centerCrop().override(1050, 480)).into(holder.thumbnail);
        Glide.with(context).load(listTrashEntity.get(position).thumbPath).apply(new RequestOptions().centerCrop()).into(holder.profile_thumbnail);
        holder.name.setText(listTrashEntity.get(position).namePath);
        String text = context.getString(R.string.home_category, listTrashEntity.get(position).getCategoryno());
        holder.price.setText(text);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, DetailActivity.class);
                intent.putExtra("EXTRA", (Parcelable) listTrashEntity.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTrashEntity.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail, profile_thumbnail;
        TextView price, name, weight;
        Button btnBuy;


        ViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.item_layout_label_price);
            name = itemView.findViewById(R.id.label_name);
            thumbnail = itemView.findViewById(R.id.item_layout_thumnail);
            profile_thumbnail = itemView.findViewById(R.id.profile_thumbnail);

            //   itemView.setOnClickListener(new adapterListener());

        }
    }

    public void setListMvContent(ArrayList<TrashEntitiy> listMvContent) {
        this.listTrashEntity = listMvContent;
    }


    }

