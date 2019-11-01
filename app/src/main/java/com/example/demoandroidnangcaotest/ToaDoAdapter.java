package com.example.demoandroidnangcaotest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class ToaDoAdapter extends RecyclerView.Adapter<ToaDoAdapter.ToaDoHolder> {

    private List<ToaDo_db> toaDo_dbList;
    private Context context;
    ToaDoDAO toaDoDAO;


    public ToaDoAdapter(List<ToaDo_db> toaDo_dbList, Context context) {
        this.toaDo_dbList = toaDo_dbList;
        this.context = context;

    }

    @NonNull
    @Override
    public ToaDoAdapter.ToaDoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new ToaDoAdapter.ToaDoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToaDoAdapter.ToaDoHolder holder, final int position) {


        holder.kinhdo.setText(String.valueOf(toaDo_dbList.get(position).getKinhdo()));
        holder.vido.setText(String.valueOf(toaDo_dbList.get(position).getVido()));
        holder.title.setText(String.valueOf(toaDo_dbList.get(position).getTitle()));

        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toaDoDAO = new ToaDoDAO(context);

                toaDoDAO.deletema(toaDo_dbList.get(position));
                notifyDataSetChanged();
                toaDo_dbList.remove(position);
            }
        });

        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double kd = toaDo_dbList.get(position).getKinhdo();
                double vido = toaDo_dbList.get(position).getVido();

                Intent intent = new Intent(context,GoogleMapsActivity.class);

                intent.putExtra("kd",kd);
                intent.putExtra("vido",vido);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return toaDo_dbList.size();
    }

    public class ToaDoHolder extends RecyclerView.ViewHolder {
         private  TextView kinhdo,vido,title;
        private Button xoa,start;
        public ToaDoHolder(@NonNull View itemView) {
            super(itemView);
            kinhdo=itemView.findViewById(R.id.tvkinhdo);
            vido=itemView.findViewById(R.id.tvvido);
            title=itemView.findViewById(R.id.tvtitle);
            xoa=itemView.findViewById(R.id.btnxoa);
            start=itemView.findViewById(R.id.btnstart);

        }
    }
}
