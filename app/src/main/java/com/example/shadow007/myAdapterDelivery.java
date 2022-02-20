package com.example.shadow007;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
public class myAdapterDelivery extends RecyclerView.Adapter<com.example.shadow007.myAdapterDelivery.ProductViewHolder> {
    private FirebaseAuth mAuth;
    private FirebaseUser user;



    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<User> users;

    //getting the context and product list with constructor
    public myAdapterDelivery(Context mCtx, List<User> productList) {
        this.mCtx = mCtx;
//        if(from.equals("volonter")){
//            this.Needs = sort(productList);}
//        else{
//            this.Needs=productList;
//        }
        this.users=productList;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

    }

    @Override
    public com.example.shadow007.myAdapterDelivery.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row4, parent, false);
        return new com.example.shadow007.myAdapterDelivery.ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(com.example.shadow007.myAdapterDelivery.ProductViewHolder holder, int position) {
        //getting the product of the specified position
        User user = users.get(position);


        holder.textViewName.setText(user.getName()+" "+user.getSurname());
        holder.textViewPhone.setText(user.getPhone());
        holder.textViewEmail.setText(user.getEmail());


        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mCtx, need.getType(), Toast.LENGTH_SHORT).show();
                funk(user.getUid());
            }
        });




    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPhone,textViewEmail;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name_sur);
            textViewPhone = itemView.findViewById(R.id.phone);
            textViewEmail = itemView.findViewById(R.id.email);

        }

    }
    private void funk(String text) {

//        Intent intent = new Intent(mCtx, OrderViewActivity.class);
//        intent.putExtra("order", String.valueOf(text));
//        mCtx.startActivity(intent);
}

}
