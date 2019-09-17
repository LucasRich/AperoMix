package com.lucasri.aperomix.View.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.lucasri.aperomix.model.Player;
import com.lucasri.aperomix.R;

import java.util.ArrayList;

public class PlayerAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Player> playerList;

    public PlayerAdapter (Context context, ArrayList<Player> list){

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

    static class ViewHolder{
        TextView playerEditText;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.player_input, null, true);

            holder.playerEditText = (EditText) convertView.findViewById(R.id.enter_players);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        if (playerList.get(position).getPlayerName().contains("Joueur")){
            holder.playerEditText.setHint(playerList.get(position).getPlayerName());
        } else {
            holder.playerEditText.setText(playerList.get(position).getPlayerName());
        }

        holder.playerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                playerList.get(position).setPlayerName(holder.playerEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return convertView;
    }
}
