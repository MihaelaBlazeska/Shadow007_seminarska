package com.example.shadow007;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class myAdapterOrders extends RecyclerView.Adapter<com.example.shadow007.myAdapterOrders.ProductViewHolder> {
        private FirebaseAuth mAuth;
        private FirebaseUser user;
        String kor;



        //this context we will use to inflate the layout
        private Context mCtx;

        //we are storing all the products in a list
        private List<Order> orders;

        //getting the context and product list with constructor
        public myAdapterOrders(Context mCtx, List<Order> productList) {
            this.mCtx = mCtx;


            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            String ostanato = user.getDisplayName().toString();
            String[] podatoci = ostanato.split(" ");
            kor = podatoci[0];
            if(kor.equals("delivery")){
            this.orders = sort(productList);}
        else{
            this.orders=productList;
        }


        }

        @Override
        public com.example.shadow007.myAdapterOrders.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row3, parent, false);
            return new com.example.shadow007.myAdapterOrders.ProductViewHolder(v);
        }

        @Override
        public void onBindViewHolder(com.example.shadow007.myAdapterOrders.ProductViewHolder holder, int position) {
            //getting the product of the specified position
            Order order = orders.get(position);
            String o=order.getUser();
            if(kor.equals("delivery")){
            com.google.android.gms.maps.model.LatLng myLocation,loc2;
            myLocation=new com.google.android.gms.maps.model.LatLng(41.9981,21.4254);
            Location startPoint=new Location("locationA");
            startPoint.setLatitude(myLocation.latitude);
            startPoint.setLongitude(myLocation.longitude);
            Location endPoint=new Location("locationA");
            if(order.getDelivery_add()!=null){
                endPoint.setLatitude(order.getDelivery_add().getLatitude());
                endPoint.setLongitude(order.getDelivery_add().getLongitude());
                loc2=new LatLng(order.getDelivery_add().getLatitude(),order.getDelivery_add().getLongitude());}

                double distance=startPoint.distanceTo(endPoint)/1000;
            holder.textViewDist.setText(String.valueOf(distance+" km"));
            holder.textViewDist.setVisibility(View.VISIBLE);}


            holder.textViewName.setText("order num: "+String.valueOf(order.getId()));
            if(kor.equals("admin")){
                holder.textViewStatus.setVisibility(View.VISIBLE);
            if(order.getProcessed()==0){
                holder.textViewStatus.setTextColor(Color.rgb(255,0,0));
                holder.textViewStatus.setText("not processed");

            }else{
                holder.textViewStatus.setTextColor(Color.rgb(0,255,0));
                holder.textViewStatus.setText("processed");

            }}
            if(order.getDelivered()==0){
                holder.textViewDelivered.setTextColor(Color.rgb(255,0,0));
                holder.textViewDelivered.setText("not delivered");

            }else{
                holder.textViewDelivered.setTextColor(Color.rgb(0,255,0));
                holder.textViewDelivered.setText("delivered");

            }

            holder.textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(mCtx, need.getType(), Toast.LENGTH_SHORT).show();
                    funk(order.getId());
                }
            });




        }

        @Override
        public int getItemCount() {
            return orders == null ? 0 : orders.size();
        }


        class ProductViewHolder extends RecyclerView.ViewHolder {

            TextView textViewName, textViewStatus,textViewDelivered,textViewDist;
            Button accept,accept_del,ship;

            public ProductViewHolder(View itemView) {
                super(itemView);

                textViewName = itemView.findViewById(R.id.ordernum);
                textViewStatus = itemView.findViewById(R.id.status);
                textViewDelivered=itemView.findViewById(R.id.delivered);
                textViewDist=itemView.findViewById(R.id.distance);

            }

        }
    private void funk(int text) {

        Intent intent = new Intent(mCtx, OrderViewActivity.class);
        intent.putExtra("order", String.valueOf(text));
        mCtx.startActivity(intent);}


    public List<Order> sort(List<Order> mArrayList) {

        // Toast.makeText(mCtx,"Vlegov vo sort",Toast.LENGTH_SHORT).show();
        List<Order> newlist=mArrayList;
        int len= newlist.size();
        double distance1=0,distance2=0;
        int i=0,j;
        Order pom;
        for(i=0;i<len-1;i++){
            for (j = 0; j < len-i-1; j++){
                LatLng myLocation=new com.google.android.gms.maps.model.LatLng(41.9981,21.4254);
                Location startPoint=new Location("locationA");
                startPoint.setLatitude(myLocation.latitude);
                startPoint.setLongitude(myLocation.longitude);
                Location endPoint=new Location("locationA");
                if(newlist.get(j).getDelivery_add()!=null){
                    pom=newlist.get(j);
                    endPoint.setLatitude(pom.getDelivery_add().getLatitude());
                    endPoint.setLongitude(pom.getDelivery_add().getLongitude());
                    distance1=startPoint.distanceTo(endPoint)/1000;
                }
                if(newlist.get(j+1).getDelivery_add()!=null){
                    pom=newlist.get(j+1);
                    endPoint.setLatitude(pom.getDelivery_add().getLatitude());
                    endPoint.setLongitude(pom.getDelivery_add().getLongitude());
                    distance2=startPoint.distanceTo(endPoint)/1000;
                }
                // Toast.makeText(mCtx,distance1+"sporeduvam so"+distance2,Toast.LENGTH_LONG).show();
                if(distance1>=distance2){

                    pom=newlist.get(j);
                    newlist.set(j,newlist.get(j+1));
                    newlist.set(j+1,pom);

                }

            }}
        return newlist;

    }}




