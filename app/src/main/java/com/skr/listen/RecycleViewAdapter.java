package com.skr.listen;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private static final String TAG = "RecycleViewAdapter";
    private ArrayList<String> mImagesNames = new ArrayList<>();
    private ArrayList<String> mImagesDescription = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Boolean editable;
    private Context mContext;

    public RecycleViewAdapter(Context mContext, ArrayList<String> mImagesNames, ArrayList<String> mImagesDescription, ArrayList<String> mImages, Boolean editable) {
        this.mImagesNames = mImagesNames;
        this.mImagesDescription = mImagesDescription;
        this.mImages = mImages;
        this.mContext = mContext;
        this.editable = editable;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);
        holder.image_name.setText(mImagesNames.get(position));
        holder.image_description.setText(mImagesDescription.get(position));

        if (editable) {
            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImagesNames.remove(position);
                    mImagesDescription.remove(position);
                    mImages.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mImagesNames.size());
                }
            });
        }

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TrackActivity.class);
                intent.putExtra(TrackActivity.EXTRA_TRACKNAME, mImagesNames.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImagesNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView image_name;
        TextView image_description;
        Button button;
        ConstraintLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            image_name = itemView.findViewById(R.id.image_name);
            image_description = itemView.findViewById(R.id.image_description);
            button = itemView.findViewById(R.id.button);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
