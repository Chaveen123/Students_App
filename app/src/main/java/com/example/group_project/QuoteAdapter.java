package com.example.group_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteViewer>{
    Context context;
    List<QuoteResponse> list;
    CopyListener listener;

    public QuoteAdapter(Context context, List<QuoteResponse> list, CopyListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuoteViewer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuoteViewer(LayoutInflater.from(context).inflate(R.layout.quotes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewer holder, int position) {
        holder.quotes_text.setText(list.get(position).getText());
        holder.author_text.setText(list.get(position).getAuthor());
        holder.button_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCopyText(list.get(holder.getAdapterPosition()).getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class QuoteViewer extends RecyclerView.ViewHolder {

    TextView quotes_text, author_text;
    Button button_copy;

    public QuoteViewer(@NonNull View itemView) {
        super(itemView);
        quotes_text = itemView.findViewById(R.id.quotes_text);
        author_text = itemView.findViewById(R.id.author_text);
        button_copy = itemView.findViewById(R.id.button_copy);
    }
}
