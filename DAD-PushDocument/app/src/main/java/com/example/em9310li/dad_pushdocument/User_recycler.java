package com.example.em9310li.dad_pushdocument;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by EM9310LI on 2017-09-28.
 */


public class User_recycler extends RecyclerView.Adapter<User_recycler.UserViewHolder> {

    private User user;

    public User_recycler(User user) {
        this.user = user;
    }

    @Override
    public User_recycler.UserViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile, parent, false);
        return new User_recycler.UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(User_recycler.UserViewHolder holder, int position){
        holder.textViewName.setText(user.getUserName());
        holder.textViewEmail.setText(user.getEmail());
    }
    @Override
    public int getItemCount(){
        Log.v(User_recycler.class.getSimpleName(), "" + user);
        return user.getUserID();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;


        public UserViewHolder(View view){
            super(view);

           // textViewEmail =(AppCompatTextView) view.findViewById(R.id.user_profile_short_bio);

        }
    }
}

