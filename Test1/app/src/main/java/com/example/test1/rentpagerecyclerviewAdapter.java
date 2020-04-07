package com.example.test1;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class rentpagerecyclerviewAdapter extends RecyclerView.ViewHolder {
    public View mview;
    public TextView  brand_seller , timeUsed_seller , price_seller ;
    public ImageView clothes;

    rentpagerecyclerviewAdapter(@NonNull View itemView) {
        super(itemView);
        mview = itemView;

        brand_seller = mview.findViewById(R.id.brand_seller);
        timeUsed_seller = mview.findViewById(R.id.timeUsed_seller);

        price_seller = mview.findViewById(R.id.price_seller);
        clothes = mview.findViewById(R.id.ClothImage);

    }

    void setDetails(String brand, String timeUsed, String url, String price )
    {

        brand_seller.setText("Brand - "+brand);
        timeUsed_seller.setText("Time Used - "+timeUsed);
        Picasso.get().load(url).into(clothes);
        price_seller.setText("Price - "+price);
    }
}
