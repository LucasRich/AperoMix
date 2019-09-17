package com.lucasri.aperomix.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lucasri.aperomix.R;
import com.lucasri.aperomix.model.Player;

import java.util.ArrayList;

public class BeLuckyGameAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Player> playerList;

    public BeLuckyGameAdapter(Context context, ArrayList<Player> list){

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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final BeLuckyGameAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new BeLuckyGameAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_be_lucky_game_input, null, true);

            //INIT
            holder.playerName = (TextView) convertView.findViewById(R.id.playerName);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (BeLuckyGameAdapter.ViewHolder)convertView.getTag();
        }

        holder.playerName.setTextColor(playerList.get(position).getColor());
        holder.playerName.setText(playerList.get(position).getPlayerName());

        return convertView;
    }
}