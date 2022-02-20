package com.example.shadow007;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ProductViewHolder> {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String from;
    private String filter;
    private String sort;


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Product> Products;

    //getting the context and product list with constructor
    public myAdapter(Context mCtx, List<Product> productList, String from,String filter,String sort) {
        this.mCtx = mCtx;
//        if(from.equals("volonter")){
//            this.Needs = sort(productList);}
//        else{
//            this.Needs=productList;
//        }

        this.from=from;
        this.filter=filter;
        this.sort=sort;
        if(this.filter.equals("All")){
            this.Products=productList;
        }else {
            this.Products=filter(productList,this.filter);
        }
        if(this.sort.equals("Price Asc")){
            this.Products=sortAsc(this.Products);
        }else{
            this.Products=sortDesc(this.Products);
        }

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Product product = Products.get(position);

        //binding the data with the viewholder views
        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(String.valueOf(product.getPrice())+"mkd");
        Resources res = this.mCtx.getResources();
        String mDrawableName = product.getImage();
        int resID = res.getIdentifier(mDrawableName , "drawable", this.mCtx.getPackageName());
        Drawable drawable = res.getDrawable(resID );
        holder.img.setImageDrawable(drawable );



        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mCtx, need.getType(), Toast.LENGTH_SHORT).show();
                funk(product.getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return Products == null ? 0 : Products.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPrice;
        ImageView img;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.Name);
            textViewPrice = itemView.findViewById(R.id.price);
            img=itemView.findViewById(R.id.slika);


        }
    }
    private void funk(CharSequence text) {
        String ostanato = user.getDisplayName().toString();
        String[] podatoci = ostanato.split(" ");
        if(podatoci[0].equals("user")){
            Intent intent = new Intent(mCtx, DescActivity.class);
            intent.putExtra("model", text);
            mCtx.startActivity(intent);}
        else{
            Intent intent = new Intent(mCtx, DescActivity2.class);
            intent.putExtra("model", text);
            mCtx.startActivity(intent);
        }
    }
    public List<Product> filter(List<Product> mArrayList,String gender) {

        // Toast.makeText(mCtx,"Vlegov vo sort",Toast.LENGTH_SHORT).show();
        List<Product> newlist=new ArrayList<>();
        int len= mArrayList.size();
        int i=0,j;
        Product pom;
        for(i=0;i<len;i++){
            if(mArrayList.get(i).getGender().equals(gender)){
                newlist.add(mArrayList.get(i));


            }
        }
        return newlist;

    }
    public List<Product> sortAsc(List<Product> mArrayList) {

        // Toast.makeText(mCtx,"Vlegov vo sort",Toast.LENGTH_SHORT).show();
        List<Product> newlist=mArrayList;
        int len= newlist.size();
        int i=0,j;
        Product pom;
        for(i=0;i<len-1;i++){
            for (j = 0; j < len-i-1; j++){

                if(newlist.get(j).getPrice()>newlist.get(j+1).getPrice()){

                    pom=newlist.get(j);
                    newlist.set(j,newlist.get(j+1));
                    newlist.set(j+1,pom);

                }

            }}
        return newlist;

    }
    public List<Product> sortDesc(List<Product> mArrayList) {

        // Toast.makeText(mCtx,"Vlegov vo sort",Toast.LENGTH_SHORT).show();
        List<Product> newlist=mArrayList;
        int len= newlist.size();
        int i=0,j;
        Product pom;
        for(i=0;i<len-1;i++){
            for (j = 0; j < len-i-1; j++){

                if(newlist.get(j).getPrice()<newlist.get(j+1).getPrice()){

                    pom=newlist.get(j);
                    newlist.set(j,newlist.get(j+1));
                    newlist.set(j+1,pom);

                }

            }}
        return newlist;

    }


}
