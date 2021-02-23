package com.example.wanderertravelapp.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderertravelapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Imageviewholder> {
    private Context mcontext;
    private List<Location> mloc;

    public ImageAdapter(Context context, List<Location> locs) {

        mcontext = context;
        mloc = locs;

    }

    @NonNull
    @Override
    public Imageviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.image_item, parent, false);
        return new Imageviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Imageviewholder holder, int position) {
        Location uploadcurrent = mloc.get(position);
        holder.textviewname.setText(uploadcurrent.getName());
        Picasso.get().load(uploadcurrent.getImgID()).tag(mcontext).fit().centerCrop().into(holder.imgview);
        holder.des.setText(uploadcurrent.getDescrip());

    }

    @Override
    public int getItemCount() {
        return mloc.size();
    }

    public class Imageviewholder extends RecyclerView.ViewHolder {
        public TextView textviewname, des;
        public ImageView imgview;


        public Imageviewholder(@NonNull View itemView) {
            super(itemView);

            textviewname = itemView.findViewById(R.id.view_name);
            imgview = itemView.findViewById(R.id.img_view);
            des = itemView.findViewById(R.id.view_des);
        }
    }
}
