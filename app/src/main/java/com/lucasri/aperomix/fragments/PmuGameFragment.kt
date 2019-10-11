package com.lucasri.aperomix.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.view.adapter.PmuParamAdapter
import com.lucasri.aperomix.activities.MainActivity
import com.lucasri.aperomix.model.Player
import com.lucasri.aperomix.utils.InitGame
import com.lucasri.aperomix.utils.setToolbarTitle
import kotlinx.android.synthetic.main.fragment_pmu_game.*
import kotlinx.android.synthetic.main.info_dialog.view.*
import java.util.*

class PmuGameFragment : Fragment() {

    private val playerList = ArrayList<Player>()
    private val colorList = ArrayList<String>()
    private val rank = ArrayList<String>()

    private var picPosition = 1
    private var coeurPosition = 1
    private var treflePosition = 1
    private var caroPosition = 1
    private var iterator = true
    private var backCard = false
    private var upCard = false
    private var specialCardNb = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pmu_game, container, false)
        setHasOptionsMenu(true)
        this.initPlayerList()
        InitGame.initColorList(colorList)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle(activity!!, "Le PMU")

        piocheButton.setOnClickListener {
            if (rank.size != 3) {

                if (!colorList.isEmpty()) {

                    val random = random()

                    if (!backCard && !upCard) {
                        displayCardRandomAndMove(colorList[random])
                    }

                    if (!backCard && upCard) {
                        specialCardUp(colorList[random])
                    }

                    if (backCard && upCard) {
                        specialCardBack(colorList[random])
                    }

                    colorList.removeAt(random)
                } else {
                    println("no more card")
                }
            } else {
                rank.add(colorList[0]) //Add the last color
                var coef = 2

                for (i in rank.indices) {

                    calculateDrink(rank[i], coef)

                    coef--
                    if (coef == 0) {
                        coef--
                    } //To pass the "0"
                }
                displayAlertDialog("TABLEAU DES SCORES :\n\n" + makeStringDisplayDrink())
            }
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_pmu_game_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.rule -> displayAlertDialog("COMMENT JOUER : \n\n Cliquer sur piocher, la carte tirée fera avancer une des cartes du milieu (sur laquelle vous avez misée). Une fois que toutes les cartes ont atteint le niveau des cartes se trouvant sur les côtés, celles-ci se dévoilent, celles de gauche font reculer et celles de droite avancer. Le but est de miser sur la carte qui arrive première \n\n 1er : distribut le double de la mise\n\n2ème : distribut la mise\n\n3ème : boit la mise\n\n4ème : boit le double de la mise ")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPlayerList() {
        playerList.addAll(MainFragment.playerList)
    }

    // ---------------------
    // UTILS
    // ---------------------

    fun calculateDrink(color: String, coef: Int) {
        for (i in playerList.indices) {
            getDrinkOrTakeDrink(color, i, coef)
        }
    }

    fun getDrinkOrTakeDrink(color: String, i: Int, coef: Int) {

        val valueGet = playerList[i].giveDrinkNb
        val valueTake = playerList[i].takeDrinkNb

        when (color) {
            "PIC" -> if (coef > 0) {
                playerList[i].giveDrinkNb = valueGet + playerList[i].picNbDrink * coef
            } else {
                playerList[i].takeDrinkNb = valueTake + playerList[i].picNbDrink * coef * -1
            }
            "COEUR" -> if (coef > 0) {
                playerList[i].giveDrinkNb = valueGet + playerList[i].coeurNbDrink * coef
            } else {
                playerList[i].takeDrinkNb = valueTake + playerList[i].coeurNbDrink * coef * -1
            }
            "TREFLE" -> if (coef > 0) {
                playerList[i].giveDrinkNb = valueGet + playerList[i].trefleNbDrink * coef
            } else {
                playerList[i].takeDrinkNb = valueTake + playerList[i].trefleNbDrink * coef * -1
            }
            "CARO" -> if (coef > 0) {
                playerList[i].giveDrinkNb = valueGet + playerList[i].caroNbDrink * coef
            } else {
                playerList[i].takeDrinkNb = valueTake + playerList[i].caroNbDrink * coef * -1
            }
        }
    }

    fun makeStringDisplayDrink(): String {
        val message = StringBuilder()
        for (i in playerList.indices) {
            message.append(playerList[i].playerName + " :" + "\n")
            if (playerList[i].giveDrinkNb != 0) message.append("DONNE : " + playerList[i].giveDrinkNb + " gorgée(s)\n")
            if (playerList[i].takeDrinkNb != 0) message.append("BOIT : " + playerList[i].takeDrinkNb + " gorgée(s)\n")
            message.append("\n")
        }
        return message.toString()
    }

    fun displayAlertDialog(message: String) {
        val dialogBuilder = AlertDialog.Builder(context!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.info_dialog, null)
        dialogBuilder.setView(dialogView)

        //INIT
        dialogView.text_info.text = message

        //DISPLAY DIALOG
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun upCard(card: String) {
        when (card) {
            "PIC" -> when (picPosition) {
                1 -> {
                    card_pic_1!!.setImageResource(R.drawable.empty_card)
                    card_pic_2!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 2
                }
                2 -> {
                    card_pic_2!!.setImageResource(R.drawable.empty_card)
                    card_pic_3!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 3
                }
                3 -> {
                    card_pic_3!!.setImageResource(R.drawable.empty_card)
                    card_pic_4!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 4
                }
                4 -> {
                    card_pic_4!!.setImageResource(R.drawable.empty_card)
                    card_pic_5!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 5
                }
                5 -> {
                    card_pic_5!!.setImageResource(R.drawable.empty_card)
                    card_pic_6!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 6
                }
                6 -> {
                    card_pic_1!!.setImageResource(R.drawable.card_pic)
                    picPosition = 7
                    displayRank(card, card_pic_6)
                    removeAll(colorList, card)
                }
            }
            "COEUR" -> when (coeurPosition) {
                1 -> {
                    card_coeur_1!!.setImageResource(R.drawable.empty_card)
                    card_coeur_2!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 2
                }
                2 -> {
                    card_coeur_2!!.setImageResource(R.drawable.empty_card)
                    card_coeur_3!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 3
                }
                3 -> {
                    card_coeur_3!!.setImageResource(R.drawable.empty_card)
                    card_coeur_4!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 4
                }
                4 -> {
                    card_coeur_4!!.setImageResource(R.drawable.empty_card)
                    card_coeur_5!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 5
                }
                5 -> {
                    card_coeur_5!!.setImageResource(R.drawable.empty_card)
                    card_coeur_6!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 6
                }
                6 -> {
                    card_coeur_1!!.setImageResource(R.drawable.card_coeur)
                    coeurPosition = 7
                    displayRank(card, card_coeur_6)
                    removeAll(colorList, "COEUR")
                }
            }
            "TREFLE" -> when (treflePosition) {
                1 -> {
                    card_trefle_1!!.setImageResource(R.drawable.empty_card)
                    card_trefle_2!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 2
                }
                2 -> {
                    card_trefle_2!!.setImageResource(R.drawable.empty_card)
                    card_trefle_3!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 3
                }
                3 -> {
                    card_trefle_3!!.setImageResource(R.drawable.empty_card)
                    card_trefle_4!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 4
                }
                4 -> {
                    card_trefle_4!!.setImageResource(R.drawable.empty_card)
                    card_trefle_5!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 5
                }
                5 -> {
                    card_trefle_5!!.setImageResource(R.drawable.empty_card)
                    card_trefle_6!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 6
                }
                6 -> {
                    card_trefle_1!!.setImageResource(R.drawable.card_trefle)
                    treflePosition = 7
                    displayRank(card, card_trefle_6)
                    removeAll(colorList, "TREFLE")
                }
            }
            "CARO" -> when (caroPosition) {
                1 -> {
                    card_caro_1!!.setImageResource(R.drawable.empty_card)
                    card_caro_2!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 2
                }
                2 -> {
                    card_caro_2!!.setImageResource(R.drawable.empty_card)
                    card_caro_3!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 3
                }
                3 -> {
                    card_caro_3!!.setImageResource(R.drawable.empty_card)
                    card_caro_4!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 4
                }
                4 -> {
                    card_caro_4!!.setImageResource(R.drawable.empty_card)
                    card_caro_5!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 5
                }
                5 -> {
                    card_caro_5!!.setImageResource(R.drawable.empty_card)
                    card_caro_6!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 6
                }
                6 -> {
                    card_caro_1!!.setImageResource(R.drawable.card_caro)
                    caroPosition = 7
                    displayRank(card, card_caro_6)
                    removeAll(colorList, "CARO")
                }
            }
        }
    }

    private fun backCard(card: String) {
        when (card) {
            "PIC" -> when (picPosition) {
                1 -> {
                    card_pic_1!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 1
                }
                2 -> {
                    card_pic_2!!.setImageResource(R.drawable.empty_card)
                    card_pic_1!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 1
                }
                3 -> {
                    card_pic_3!!.setImageResource(R.drawable.empty_card)
                    card_pic_2!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 2
                }
                4 -> {
                    card_pic_4!!.setImageResource(R.drawable.empty_card)
                    card_pic_3!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 3
                }
                5 -> {
                    card_pic_5!!.setImageResource(R.drawable.empty_card)
                    card_pic_4!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 4
                }
                6 -> {
                    card_pic_6!!.setImageResource(R.drawable.empty_card)
                    card_pic_5!!.setImageResource(R.drawable.card_pic_as)
                    picPosition = 5
                }
            }
            "COEUR" -> when (coeurPosition) {
                1 -> {
                    card_coeur_1!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 1
                }
                2 -> {
                    card_coeur_2!!.setImageResource(R.drawable.empty_card)
                    card_coeur_1!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 1
                }
                3 -> {
                    card_coeur_3!!.setImageResource(R.drawable.empty_card)
                    card_coeur_2!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 2
                }
                4 -> {
                    card_coeur_4!!.setImageResource(R.drawable.empty_card)
                    card_coeur_3!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 3
                }
                5 -> {
                    card_coeur_5!!.setImageResource(R.drawable.empty_card)
                    card_coeur_4!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 4
                }
                6 -> {
                    card_coeur_6!!.setImageResource(R.drawable.empty_card)
                    card_coeur_5!!.setImageResource(R.drawable.card_coeur_as)
                    coeurPosition = 5
                }
            }
            "TREFLE" -> when (treflePosition) {
                1 -> {
                    card_trefle_1!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 1
                }
                2 -> {
                    card_trefle_2!!.setImageResource(R.drawable.empty_card)
                    card_trefle_1!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 1
                }
                3 -> {
                    card_trefle_3!!.setImageResource(R.drawable.empty_card)
                    card_trefle_2!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 2
                }
                4 -> {
                    card_trefle_4!!.setImageResource(R.drawable.empty_card)
                    card_trefle_3!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 3
                }
                5 -> {
                    card_trefle_5!!.setImageResource(R.drawable.empty_card)
                    card_trefle_4!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 4
                }
                6 -> {
                    card_trefle_6!!.setImageResource(R.drawable.empty_card)
                    card_trefle_5!!.setImageResource(R.drawable.card_trefle_as)
                    treflePosition = 5
                }
            }
            "CARO" -> when (caroPosition) {
                1 -> {
                    card_caro_1!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 1
                }
                2 -> {
                    card_caro_2!!.setImageResource(R.drawable.empty_card)
                    card_caro_1!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 1
                }
                3 -> {
                    card_caro_3!!.setImageResource(R.drawable.empty_card)
                    card_caro_2!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 2
                }
                4 -> {
                    card_caro_4!!.setImageResource(R.drawable.empty_card)
                    card_caro_3!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 3
                }
                5 -> {
                    card_caro_5!!.setImageResource(R.drawable.empty_card)
                    card_caro_4!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 4
                }
                6 -> {
                    card_caro_6!!.setImageResource(R.drawable.empty_card)
                    card_caro_5!!.setImageResource(R.drawable.card_caro_as)
                    caroPosition = 5
                }
            }
        }
    }

    private fun displayCardRandomAndMove(card: String) {
        when (card) {
            "PIC" -> moveCardRandomWithCountDown(card_random, R.drawable.card_pic_as, card, true)
            "COEUR" -> moveCardRandomWithCountDown(card_random, R.drawable.card_coeur_as, card, true)
            "TREFLE" -> moveCardRandomWithCountDown(card_random, R.drawable.card_trefle_as, card, true)
            "CARO" -> moveCardRandomWithCountDown(card_random, R.drawable.card_caro_as, card, true)
        }
    }

    private fun specialCardBack(card: String) {
        when (specialCardNb) {
            1 -> displayBackAndUpCardAndMove(card, card_back_1, false)
            2 -> displayBackAndUpCardAndMove(card, card_back_2, false)
            3 -> displayBackAndUpCardAndMove(card, card_back_3, false)
            4 -> displayBackAndUpCardAndMove(card, card_back_4, false)
            5 -> displayBackAndUpCardAndMove(card, card_back_5, false)
        }
        backCard = false
    }

    private fun specialCardUp(card: String) {
        when (specialCardNb) {
            1 -> displayBackAndUpCardAndMove(card, card_up_1, true)
            2 -> displayBackAndUpCardAndMove(card, card_up_2, true)
            3 -> displayBackAndUpCardAndMove(card, card_up_3, true)
            4 -> displayBackAndUpCardAndMove(card, card_up_4, true)
            5 -> displayBackAndUpCardAndMove(card, card_up_5, true)
        }
        upCard = false
        specialCardNb++
    }

    fun displayBackAndUpCardAndMove(card: String, randomCard: ImageView?, up: Boolean) {
        when (card) {
            "PIC" -> moveCardRandomWithCountDown(randomCard, R.drawable.card_pic, card, up)
            "COEUR" -> moveCardRandomWithCountDown(randomCard, R.drawable.card_coeur, card, up)
            "TREFLE" -> moveCardRandomWithCountDown(randomCard, R.drawable.card_trefle, card, up)
            "CARO" -> moveCardRandomWithCountDown(randomCard, R.drawable.card_caro, card, up)
        }
    }

    private fun moveCardRandomWithCountDown(randomCard: ImageView?, cardRessource: Int, card: String, up: Boolean) {
        piocheButton!!.isEnabled = false
        object : CountDownTimer(1500, 200) {
            override fun onTick(millisUntilFinished: Long) {
                randomCard!!.setImageResource(R.drawable.card_random)

                if (iterator) {
                    randomCard.animate().rotation(30f)
                    iterator = false
                } else {
                    randomCard.animate().rotation(-30f)
                    iterator = true
                }
            }

            override fun onFinish() {
                randomCard!!.animate().rotation(0f)
                randomCard.setImageResource(cardRessource)

                if (up) {
                    upCard(card)
                } else {
                    backCard(card)
                }

                piocheButton!!.isEnabled = true

                if (picPosition >= specialCardNb + 1 && coeurPosition >= specialCardNb + 1 && treflePosition >= specialCardNb + 1 && caroPosition >= specialCardNb + 1) {

                    if (!backCard && !upCard) {
                        backCard = true
                        upCard = true
                    }
                }
            }
        }.start()
    }

    private fun displayRank(card: String, displayCard: ImageView?) {
        rank.add(card)
        when (rank.indexOf(card)) {
            0 -> displayCard!!.setImageResource(R.drawable.premier)
            1 -> displayCard!!.setImageResource(R.drawable.deuxieme)
            2 -> displayCard!!.setImageResource(R.drawable.troisieme)
            3 -> displayCard!!.setImageResource(R.drawable.quatrieme)
        }
    }

    internal fun removeAll(list: MutableList<String>, element: String) {
        while (list.contains(element)) {
            list.remove(element)
        }
    }

    private fun random(): Int {
        val nbRandom = Math.random() * colorList.size
        return nbRandom.toInt()
    }

    private fun launchMainActivity() {
        val myIntent: Intent = Intent(activity, MainActivity::class.java)
        this.startActivity(myIntent)
    }
}
