package com.example.shadow007;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class myAdapter2 extends RecyclerView.Adapter<com.example.shadow007.myAdapter2.ProductViewHolder> {
        private FirebaseAuth mAuth;
        private FirebaseUser user;
        private String from;


        //this context we will use to inflate the layout
        private Context mCtx;

        //we are storing all the products in a list
        private List<Product> Products;
    private List<Integer> Quantity;

        //getting the context and product list with constructor
        public myAdapter2(Context mCtx, List<Product> productList, String from,List<Integer> quan) {
            this.mCtx = mCtx;
//        if(from.equals("volonter")){
//            this.Needs = sort(productList);}
//        else{
//            this.Needs=productList;
//        }
            this.Products=productList;
            this.Quantity=quan;
            this.from=from;
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();

        }

        @Override
        public com.example.shadow007.myAdapter2.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row2, parent, false);
            return new com.example.shadow007.myAdapter2.ProductViewHolder(v);
        }

        @Override
        public void onBindViewHolder(com.example.shadow007.myAdapter2.ProductViewHolder holder, int position) {
            //getting the product of the specified position
            Product product = Products.get(position);
            int quantity=Quantity.get(position);
            CartViewActivity.final_price+= product.getPrice()*quantity;


            //binding the data with the viewholder views
            holder.textViewName.setText(product.getName());
            holder.textViewQuantity.setText(String.valueOf(quantity));
            if(product.getDiscount()!=0){
                holder.textViewPrice.setTextColor(Color.rgb(255,0,0));
                holder.textViewPrice.setText(String.valueOf(product.getPrice()-(product.getPrice()/100*product.getDiscount()))+"mkd (-"+String.valueOf(product.getDiscount())+"%)");

            }else{
                holder.textViewPrice.setTextColor(Color.rgb(0,0,0));
                holder.textViewPrice.setText(String.valueOf(product.getPrice())+"mkd");

            }



        }

        @Override
        public int getItemCount() {
            return Products == null ? 0 : Products.size();
        }


        class ProductViewHolder extends RecyclerView.ViewHolder {

            TextView textViewName, textViewPrice,textViewQuantity,textViewTotal;

            public ProductViewHolder(View itemView) {
                super(itemView);

                textViewName = itemView.findViewById(R.id.Name);
                textViewPrice = itemView.findViewById(R.id.price);
                textViewQuantity=itemView.findViewById(R.id.quantity);

            }
        }

}
