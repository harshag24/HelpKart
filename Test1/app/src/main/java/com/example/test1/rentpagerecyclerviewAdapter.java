package com.example.test1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class rentpagerecyclerviewAdapter extends RecyclerView.Adapter<rentpagerecyclerviewAdapter.rentpageviewholder>
{
    private String[] data;
 public rentpagerecyclerviewAdapter(String []data)
 {
this.data =data;
 }
    @Override
    public rentpageviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rentpage_recycler_view_list_item , parent ,false);
        return new rentpageviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rentpageviewholder holder, int position) {
String title = data[position];
holder.txtTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public  class rentpageviewholder extends RecyclerView.ViewHolder {
     ImageView imgIcon;
     TextView txtTitle;
        public rentpageviewholder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
