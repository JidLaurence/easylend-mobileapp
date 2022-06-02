package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterForMemberList extends RecyclerView.Adapter<CustomAdapterForMemberList.MyViewHolderMemberList> {

    Context context;
    public ArrayList id, firstname, capital, purok, balance, amount ;

    private MemberListClickListener listener;

    //firstname, capital - purok, balance, amount


    CustomAdapterForMemberList(Context context, ArrayList id, ArrayList firstname, ArrayList purok, ArrayList amount, MemberListClickListener listener){
        this.context = context;

        this.id = id;
        this.firstname = firstname;
        this.capital = capital;
        this.purok = purok;
        this.balance = balance;
        this.amount = amount;



        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolderMemberList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.member_list_data_view, parent, false);
        return new MyViewHolderMemberList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMemberList holder, int position) {

        holder.firstName_TV.setText(String.valueOf(firstname.get(position)));
        holder.address_TV.setText(String.valueOf(purok.get(position)));
        holder.amount_TV.setText(String.valueOf(amount.get(position)));
    }

    @Override
    public int getItemCount() {
        return firstname.size();
    }

    public class MyViewHolderMemberList extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView id_TV, firstName_TV, address_TV, amount_TV;

        public MyViewHolderMemberList(@NonNull View itemView) {
            super(itemView);

            firstName_TV = itemView.findViewById(R.id.firstName_TV);
            address_TV = itemView.findViewById(R.id.address_TV);
            amount_TV = itemView.findViewById(R.id.amount_TV);


        itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public interface MemberListClickListener{
        void onClick(View v, int position);
    }


}
