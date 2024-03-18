package com.example.placement_desk.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placement_desk.Model.Year;
import com.example.placement_desk.R;

import java.util.List;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {
    private List<Year> yearList;
    private OnYearClickListener listener;

    public interface OnYearClickListener {
        void onYearClick(String year);
    }

    public YearAdapter(List<Year> yearList, OnYearClickListener listener) {
        this.yearList = yearList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Year year = yearList.get(position);
        holder.yearTextView.setText(year.getYear());
    }

    @Override
    public int getItemCount() {
        return yearList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView yearTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            yearTextView = itemView.findViewById(R.id.year);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Year year = yearList.get(position);
                listener.onYearClick(year.getYear());
            }
        }
    }
}
