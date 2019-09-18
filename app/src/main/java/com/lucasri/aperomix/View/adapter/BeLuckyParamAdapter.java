package com.lucasri.aperomix.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lucasri.aperomix.R;
import com.lucasri.aperomix.model.Player;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;

public class BeLuckyParamAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Player> playerList;

    public BeLuckyParamAdapter(Context context, ArrayList<Player> list){

        this.context = context;
        this.playerList = list;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return playerList.size();
    }

    @Override
    public Object getItem(int position) {
        return playerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView playerName;
        ImageView playerColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final BeLuckyParamAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new BeLuckyParamAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_be_lucky_param_input, null, true);

            //INIT
            holder.playerName = (TextView) convertView.findViewById(R.id.playerName);
            holder.playerColor = (ImageView) convertView.findViewById(R.id.playerColor);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (BeLuckyParamAdapter.ViewHolder)convertView.getTag();
        }

        holder.playerName.setText(playerList.get(position).getPlayerName());

        holder.playerColor.setOnClickListener(v -> {

            SpectrumDialog.Builder bu = new SpectrumDialog.Builder(context);
            bu.setColors(R.array.player_colors);
            bu.setTitle("Choisis ta couleur");
            bu.setSelectedColor(playerList.get(position).getColor());
            bu.setOnColorSelectedListener((positiveResult, color1) -> {

                playerList.get(position).setColor(color1);
                holder.playerColor.setColorFilter(color1);
            });
            bu.build().show(((AppCompatActivity) context).getSupportFragmentManager(),"tag");

        });

        return convertView;
    }
}