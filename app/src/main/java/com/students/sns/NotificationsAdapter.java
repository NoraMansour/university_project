package com.students.sns;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by meem on 9/13/2017.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>  {
    Context context;
    List<Notifications> nList;

    public NotificationsAdapter(Context context, List<Notifications> nList) {
        this.context = context;
        this.nList = nList;
        Toast.makeText(context, "count of items "+String.valueOf(nList.size()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Notifications notify=nList.get(position);
        holder.n_sender.setText(notify.getN_sender());
        holder.n_date.setText(notify.getN_date());
        holder.n_title.setText(notify.getN_title());
        final String n_body=notify.getN_text();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent=new Intent(context,NotifyDetails.class);
                detailsIntent.putExtra("sender",holder.n_sender.getText().toString());
                detailsIntent.putExtra("title",holder.n_title.getText().toString());
                detailsIntent.putExtra("date",holder.n_date.getText().toString());
                detailsIntent.putExtra("body",n_body);
                context.startActivity(detailsIntent);
            }
        });
      //  Toast.makeText(context, holder.n_sender.getText().toString(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public int getItemCount() {
      //  Toast.makeText(context, "count of items "+String.valueOf(nList.size()), Toast.LENGTH_SHORT).show();
        return nList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView n_sender,n_date,n_title;


        public ViewHolder(View itemView) {
            super(itemView);
            n_sender=(TextView)itemView.findViewById(R.id.n_sender);
            n_date=(TextView)itemView.findViewById(R.id.n_date);
            n_title=(TextView)itemView.findViewById(R.id.n_title);
        }
    }
}
