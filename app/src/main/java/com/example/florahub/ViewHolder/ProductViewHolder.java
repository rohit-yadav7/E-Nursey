package com.example.florahub.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.florahub.Interface.ItemClickListner;
import com.example.florahub.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName,txtProductDescription,txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;
    View mView;

    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);
        mView=itemView;
        imageView=(ImageView)mView.findViewById(R.id.product_image);
        txtProductName=(TextView) mView.findViewById(R.id.product_name);
        txtProductDescription=(TextView)mView.findViewById(R.id.product_description);
        txtProductPrice=(TextView)mView.findViewById(R.id.product_price);

    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner=listner;
    }


    @Override
    public void onClick(View view)
    {
        listner.onClick(view,getAdapterPosition(),false );
    }
}
