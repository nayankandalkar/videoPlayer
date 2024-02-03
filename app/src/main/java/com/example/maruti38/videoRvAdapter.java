package com.example.maruti38;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class videoRvAdapter extends RecyclerView.Adapter<videoRvAdapter.ViewHolder> {

    ArrayList<videoRvModel> videoRvModelsArraylist;
    Context context;
    videoClickInterface videoClickInterface;

    public videoRvAdapter(ArrayList<videoRvModel> videoRvModelsArraylist, Context context, videoRvAdapter.videoClickInterface videoClickInterface) {
        this.videoRvModelsArraylist = videoRvModelsArraylist;
        this.context = context;
        this.videoClickInterface = videoClickInterface;
    }

    @NonNull
    @Override
    public videoRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_rv_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull videoRvAdapter.ViewHolder holder, int position) {

        videoRvModel videoRvModel=videoRvModelsArraylist.get(position);
        holder.thumbnailTv.setImageBitmap(videoRvModel.getThumbnail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoClickInterface.onvideoClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoRvModelsArraylist.size();
    }

    public class ViewHolder   extends  RecyclerView.ViewHolder{

        ImageView thumbnailTv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailTv=itemView.findViewById(R.id.idTvThumbNail);

        }
    }


    public  interface videoClickInterface{
       void  onvideoClick(int position);
    }
}
