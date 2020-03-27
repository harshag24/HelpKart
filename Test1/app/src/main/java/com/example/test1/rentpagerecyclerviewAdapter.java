package com.example.test1;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class rentpagerecyclerviewAdapter extends RecyclerView.ViewHolder {
    private View mview;
    private TextView name_seller , phone_seller , brand_seller , timeUsed_seller , desc_seller;
    private ImageView clothes;

    rentpagerecyclerviewAdapter(@NonNull View itemView) {
        super(itemView);
        mview = itemView;
        name_seller = mview.findViewById(R.id.name_seller);
        phone_seller = mview.findViewById(R.id.phone_seller);
        brand_seller = mview.findViewById(R.id.brand_seller);
        timeUsed_seller = mview.findViewById(R.id.timeUsed_seller);
        desc_seller = mview.findViewById(R.id.desc_seller);
        clothes = mview.findViewById(R.id.ClothImage);
    }

    void setDetails(String name, String phone_no, String Brand, String timeUsed, String Desc, String url)
    {
        name_seller.setText("Name- "+name);
        phone_seller.setText("Phone No- "+phone_no);
        Log.e(TAG, "setDetails: WTF");
        brand_seller.setText("Brand- "+Brand);
        timeUsed_seller.setText("Time Used for- "+timeUsed);
        desc_seller.setText("Description- "+Desc);
        Picasso.get().load(url).into(clothes);
    }
}
