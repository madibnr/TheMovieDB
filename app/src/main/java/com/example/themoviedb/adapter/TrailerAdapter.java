package com.example.themoviedb.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.model.TrailerModel;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private List<TrailerModel.Results> trailers;
    private Context context;
    private OnAdapterListener listener;


    public TrailerAdapter(List<TrailerModel.Results> trailers, Context context, OnAdapterListener listener) {
        this.trailers = trailers;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from( parent.getContext() ).inflate(R.layout.activity_trailer, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrailerModel.Results trailer = trailers.get(position);
        holder.textView.setText( trailer.getName() );
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick( trailer .getKey());
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_name);
        }
    }
    public void setData(List<TrailerModel.Results> newTrailer){
        trailers.clear();
        trailers.addAll(newTrailer);
        notifyDataSetChanged();
        listener.OnLoadVideo( newTrailer.get( 0 ).getKey());
    }
   public interface OnAdapterListener {
        void OnClick(String key);
       void OnLoadVideo(String key);
   }
}
