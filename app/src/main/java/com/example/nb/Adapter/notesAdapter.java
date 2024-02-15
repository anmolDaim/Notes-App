package com.example.nb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nb.DataModel.itemDataModel;
import com.example.nb.R;

import java.util.ArrayList;

public class notesAdapter extends RecyclerView.Adapter<notesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<itemDataModel> arr;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public notesAdapter(Context context,  ArrayList<itemDataModel> arr,OnItemClickListener listener){
        this.context=context;
        this.arr=arr;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.NoteLayout.setText(arr.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView NoteLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NoteLayout=itemView.findViewById(R.id.noteLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }
}
