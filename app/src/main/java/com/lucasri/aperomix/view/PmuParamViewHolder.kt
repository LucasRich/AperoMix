package com.lucasri.aperomix.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.models.Player
import kotlinx.android.synthetic.main.pmu_param_item.view.*

class PmuParamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val view: View = view
    private var nbPic: Int = 0
    private var nbCoeur: Int = 0
    private var nbTrefle: Int = 0
    private var nbCaro: Int = 0

    fun updateWithPlayerList(playerList: Player) {
        init()

        view.display_player.text = playerList.playerName

        //ADD BUTTON

        view.choice_card_pic_add.setOnClickListener {
            view.choice_card_pic_nb.text = playerList.picNbDrink.toString()
            nbPic++
            view.choice_card_pic_nb.text = "" + nbPic
            playerList.picNbDrink = nbPic
        }

        view.choice_card_coeur_add.setOnClickListener {
            nbCoeur = playerList.coeurNbDrink
            nbCoeur++
            view.choice_card_coeur_nb.text = "" + nbCoeur
            playerList.coeurNbDrink = nbCoeur
        }

        view.choice_card_trefle_add.setOnClickListener {
            nbTrefle = playerList.trefleNbDrink
            nbTrefle++
            view.choice_card_trefle_nb.text = "" + nbTrefle
            playerList.trefleNbDrink = nbTrefle
        }

        view.choice_card_caro_add.setOnClickListener {
            nbCaro = playerList.caroNbDrink
            nbCaro++
            view.choice_card_caro_nb.text = "" + nbCaro
            playerList.caroNbDrink = nbCaro
        }

        //REMOVE BUTTON

        view.choice_card_pic_remove.setOnClickListener {
            if (nbPic > 0) {
                nbPic = playerList.picNbDrink
                nbPic--
                view.choice_card_pic_nb.text = "" + nbPic
                playerList.picNbDrink = nbPic
            }
        }

        view.choice_card_coeur_remove.setOnClickListener {
            if (nbCoeur > 0) {
                nbCoeur = playerList.coeurNbDrink
                nbCoeur--
                view.choice_card_coeur_nb.text = "" + nbCoeur
                playerList.coeurNbDrink = nbCoeur
            }
        }

        view.choice_card_trefle_remove.setOnClickListener {
            if (nbTrefle > 0) {
                nbTrefle = playerList.trefleNbDrink
                nbTrefle--
                view.choice_card_trefle_nb.text = "" + nbTrefle
                playerList.trefleNbDrink = nbTrefle
            }
        }

        view.choice_card_caro_remove.setOnClickListener {
            if (nbCaro > 0) {
                nbCaro = playerList.caroNbDrink
                nbCaro--
                view.choice_card_caro_nb.text = "" + nbCaro
                playerList.caroNbDrink = nbCaro
            }
        }
    }

    private fun init(){
        view.choice_card_pic_nb.text = "0"
        view.choice_card_coeur_nb.text = "0"
        view.choice_card_trefle_nb.text = "0"
        view.choice_card_caro_nb.text = "0"
    }
}
