package com.example.test1;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class rentpagerecyclerviewAdapter extends RecyclerView.ViewHolder {
    View mview;
    TextView name_seller , phone_seller;
    ImageView clothes;

    public rentpagerecyclerviewAdapter(@NonNull View itemView) {
        super(itemView);
        mview = itemView;
        name_seller = mview.findViewById(R.id.name_seller);
        phone_seller = mview.findViewById(R.id.phone_seller);
        clothes = mview.findViewById(R.id.ClothImage);
    }

    public void setDetails(Context ctx, String name, String phone_no, String uri)
    {
        name_seller.setText(name);
        phone_seller.setText(phone_no);
        Picasso.get().load(uri).into(clothes);
    }
}
