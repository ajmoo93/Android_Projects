package adapters;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.em9310li.yourdailyinfo.R;
import com.example.em9310li.yourdailyinfo.User;

import java.util.List;

/**
 * Created by EM9310LI on 2017-03-08.
 */

public  class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUser;

    public UsersRecyclerAdapter(List<User>listUser){
        this.listUser = listUser;
    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_recycler, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(listUser.get(position).getName());
        holder.textViewEmail.setText(listUser.get(position).getEmail());
        holder.textViewPassword.setText(listUser.get(position).getPassword());
    }

    @Override
    public int getItemCount(){
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUser.size());
        return listUser.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;
        public AppCompatButton DeleteButton;

        public UserViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewPassword = (AppCompatTextView) view.findViewById(R.id.textViewPassword);
            DeleteButton = (AppCompatButton) view.findViewById(R.id.DeleteButton);
        }

    }

}
