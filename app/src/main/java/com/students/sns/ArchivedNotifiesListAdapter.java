package com.students.sns;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by meem on 9/22/2017.
 */

public class ArchivedNotifiesListAdapter extends RecyclerView.Adapter<ArchivedNotifiesListAdapter.ViewHolder> {
Context ctx;List<ArchivedNotifiesList> archivedList;

    public ArchivedNotifiesListAdapter(Context ctx, List<ArchivedNotifiesList> archivedList) {
        this.ctx = ctx;
        this.archivedList = archivedList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ctx).inflate(R.layout.archived_card_view,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArchivedNotifiesList archived=archivedList.get(position);
        holder.nTitle.setText(archived.getTitle());
        holder.nText.setText(archived.getText());
        holder.nCollage.setText(archived.getCollage());
        holder.nSection.setText(archived.getSection());
        holder.nDate.setText(archived.getDate());
        holder.nLevel.setText(archived.getLevel());

    }

    @Override
    public int getItemCount() {
        return archivedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
TextView nTitle,nText,nDate,nCollage,nSection,nLevel;
        public ViewHolder(View itemView) {
            super(itemView);
            nTitle=(TextView)itemView.findViewById(R.id.inst_title);
            nText=(TextView)itemView.findViewById(R.id.inst_text);
            nCollage=(TextView)itemView.findViewById(R.id.inst_collage);
            nDate=(TextView)itemView.findViewById(R.id.inst_date);
            nSection=(TextView)itemView.findViewById(R.id.inst_section);
            nLevel=(TextView)itemView.findViewById(R.id.inst_level);
        }
    }
}
