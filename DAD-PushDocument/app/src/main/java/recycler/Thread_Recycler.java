package recycler;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.em9310li.dad_pushdocument.R;
import classes.Threads;
import classes.User;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by EM9310LI on 2017-11-01.
 */

public class Thread_Recycler extends RecyclerView.Adapter<Thread_Recycler.ThreadViewHolder>{
    private List<Threads> threadsList;
    private List<User> userList;


    public Thread_Recycler(List<Threads> threadsList) {
        this.threadsList = threadsList;

    }




    @Override
    public Thread_Recycler.ThreadViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.thread_recycler_view, parent, false);
        return new Thread_Recycler.ThreadViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ThreadViewHolder holder, int position){

        holder.textViewTitle.setText(threadsList.get(position).getTitle());
        holder.appCompatTimeEditView.setText(threadsList.get(position).getDateCreated());

    }
    @Override
    public int getItemCount(){
        Log.v(Thread_Recycler.class.getSimpleName(), "" + threadsList.size());
        return threadsList.size();

    }
    public class ThreadViewHolder extends RecyclerView.ViewHolder{
        public AppCompatTextView textViewName;
        public AppCompatTextView textViewTitle;
        public AppCompatTextView appCompatTimeEditView;


        public ThreadViewHolder(View view){
            super(view);
            appCompatTimeEditView = (AppCompatTextView) view.findViewById(R.id
            .appCompatTimeEditView);
            textViewName =(AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewTitle =(AppCompatTextView) view.findViewById(R.id.textViewTitle);

        }
    }
}

