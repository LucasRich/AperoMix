package com.lucasri.aperomix.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucasri.aperomix.R;
import com.lucasri.aperomix.model.Player;

import java.util.ArrayList;

public class PmuParamAdapter extends BaseAdapter{

    private Context context;
    public static ArrayList<Player> playerList;

    private int nbPic;
    private int nbCoeur;
    private int nbTrefle;
    private int nbCaro;


    public PmuParamAdapter (Context context, ArrayList<Player> list){

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
        TextView playerEditText, nb_pic, nb_coeur, nb_trefle, nb_caro;
        ImageView add_pic, add_coeur, add_trefle, add_caro, remove_pic, remove_coeur, remove_trefle, remove_caro;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final PmuParamAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new PmuParamAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pmu_param_input, null, true);

            //INIT
            holder.playerEditText = (TextView) convertView.findViewById(R.id.display_player);
            holder.nb_pic = (TextView) convertView.findViewById(R.id.choice_card_pic_nb);
            holder.nb_coeur = (TextView) convertView.findViewById(R.id.choice_card_coeur_nb);
            holder.nb_trefle = (TextView) convertView.findViewById(R.id.choice_card_trefle_nb);
            holder.nb_caro = (TextView) convertView.findViewById(R.id.choice_card_caro_nb);

            holder.add_pic = (ImageView) convertView.findViewById(R.id.choice_card_pic_add);
            holder.add_coeur = (ImageView) convertView.findViewById(R.id.choice_card_coeur_add);
            holder.add_trefle = (ImageView) convertView.findViewById(R.id.choice_card_trefle_add);
            holder.add_caro = (ImageView) convertView.findViewById(R.id.choice_card_caro_add);

            holder.remove_pic = (ImageView) convertView.findViewById(R.id.choice_card_pic_remove);
            holder.remove_coeur = (ImageView) convertView.findViewById(R.id.choice_card_coeur_remove);
            holder.remove_trefle = (ImageView) convertView.findViewById(R.id.choice_card_trefle_remove);
            holder.remove_caro = (ImageView) convertView.findViewById(R.id.choice_card_caro_remove);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (PmuParamAdapter.ViewHolder)convertView.getTag();
        }

        holder.playerEditText.setText(playerList.get(position).getPlayerName());
        holder.nb_pic.setText("0");
        holder.nb_coeur.setText("0");
        holder.nb_trefle.setText("0");
        holder.nb_caro.setText("0");

        //ADD BUTTON

        holder.add_pic.setOnClickListener(v -> {
            nbPic = playerList.get(position).getPicNbDrink();
            nbPic++;
            holder.nb_pic.setText(""+nbPic);
            playerList.get(position).setPicNbDrink(nbPic);
        });

        holder.add_coeur.setOnClickListener(v -> {
            nbCoeur = playerList.get(position).getCoeurNbDrink();
            nbCoeur++;
            holder.nb_coeur.setText(""+nbCoeur);
            playerList.get(position).setCoeurNbDrink(nbCoeur);
        });

        holder.add_trefle.setOnClickListener(v -> {
            nbTrefle = playerList.get(position).getTrefleNbDrink();
            nbTrefle++;
            holder.nb_trefle.setText(""+nbTrefle);
            playerList.get(position).setTrefleNbDrink(nbTrefle);
        });

        holder.add_caro.setOnClickListener(v -> {
            nbCaro = playerList.get(position).getCaroNbDrink();
            nbCaro++;
            holder.nb_caro.setText(""+nbCaro);
            playerList.get(position).setCaroNbDrink(nbCaro);
        });

        //REMOVE BUTTON

        holder.remove_pic.setOnClickListener(v -> {
            if (nbPic > 0){
                nbPic = playerList.get(position).getPicNbDrink();
                nbPic--;
                holder.nb_pic.setText(""+nbPic);
                playerList.get(position).setPicNbDrink(nbPic);
            }
        });

        holder.remove_coeur.setOnClickListener(v -> {
            if (nbCoeur > 0) {
                nbCoeur = playerList.get(position).getCoeurNbDrink();
                nbCoeur--;
                holder.nb_coeur.setText("" + nbCoeur);
                playerList.get(position).setCoeurNbDrink(nbCoeur);
            }
        });

        holder.remove_trefle.setOnClickListener(v -> {
            if (nbTrefle > 0) {
                nbTrefle = playerList.get(position).getTrefleNbDrink();
                nbTrefle--;
                holder.nb_trefle.setText("" + nbTrefle);
                playerList.get(position).setTrefleNbDrink(nbTrefle);
            }
        });

        holder.remove_caro.setOnClickListener(v -> {
            if (nbCaro > 0) {
                nbCaro = playerList.get(position).getCaroNbDrink();
                nbCaro--;
                holder.nb_caro.setText("" + nbCaro);
                playerList.get(position).setCaroNbDrink(nbCaro);
            }
        });


        return convertView;
    }
}
