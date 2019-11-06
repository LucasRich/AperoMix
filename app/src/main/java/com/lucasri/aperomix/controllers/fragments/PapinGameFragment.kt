package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.activities.MainActivity
import com.lucasri.aperomix.model.Player
import com.lucasri.aperomix.utils.InitGame
import com.lucasri.aperomix.utils.longToast
import com.lucasri.aperomix.utils.random
import kotlinx.android.synthetic.main.fragment_papin_game.*
import kotlinx.android.synthetic.main.info_dialog.view.*
import java.util.ArrayList

class PapinGameFragment: Fragment(){
    private var mediaPlayer = MediaPlayer()

    private val playerList = ArrayList<Player>()
    private val ruleList = ArrayList<String>()
    private val passedRuleList = ArrayList<String>()

    private var playerCounter = 0
    private var navigationRulePlayerCounter: Int = 0
    private var passedRuleCounter: Int = 0
    private var count = 0
    private var navigationRuleCount: Int = 0
    private var random: Int = 0

    private var infoRule: String? = null
    private var previousRule = "null"
    private var caseSong: String? = null
    private val btnTxt: String? = null

    private var doubleScreen: Boolean = false
    private var boyAndGirl = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_papin_game, container, false)
        setHasOptionsMenu(true)
        this.initPlayerList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        game_txt_alone.text = "Cliquez sur l'écran pour commencer et activer le son"
        info_btn.visibility = View.INVISIBLE
        bottom_btn.visibility = View.INVISIBLE

        this.initScreen()
        this.displayNavigationRule(false)
        this.askBoyAndGirl()
        this.initPlayerList()

        game_view.setOnClickListener {
            if (ruleList.isEmpty()) {
                InitGame.initRuleList(ruleList, boyAndGirl)
            }

            if (count == navigationRuleCount) {
                displayNavigationRule(false)
                count++
                navigationRuleCount = count

                if (count < 53) {
                    displayCounter(count)

                    if (playerList.isNotEmpty()) {
                        displayPlayerName(playerList[playerCounter].playerName)
                        navigationRulePlayerCounter = playerCounter
                        playerCounter++

                        if (playerCounter == playerList.size) {
                            playerCounter = 0
                        }
                    }
                    play()
                } else {
                    gameFinish()
                }
                passedRuleCounter = passedRuleList.size - 1
            } else {
                context!!.longToast("Revenez au dernier tour de jeu")
            }
        }

        previous_rule.setOnClickListener {
            displayPreviousRule()
        }

        next_rule.setOnClickListener {
            displayNextRule()
        }

        info_btn.setOnClickListener {
            displayAlertDialog(infoRule!!)
        }

        bottom_btn.setOnClickListener {
            if (count > 52) launchMainActivity() else playCountDown()
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_papin_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.doubleScreen -> {
                if (count != 0) {
                    if (!doubleScreen) {
                        doubleScreen()
                        doubleScreen = true
                    } else {
                        initScreen()
                        doubleScreen = false
                    }
                } else {
                    context!!.longToast("La partie n'a pas encore commencé")
                }

                return true
            }
            R.id.previousRule -> {
                if (count != 0) {
                    displayNavigationRule(true)
                } else {
                    context!!.longToast("La partie n'a pas encore commencé")
                }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initPlayerList() {
        playerList.addAll(MainFragment.playerList)
    }

    private fun initScreen() {
        nameGame.visibility = View.VISIBLE
        game_txt_alone.visibility = View.VISIBLE
        counter.visibility = View.VISIBLE

        nameGame_doubleScreen.visibility = View.INVISIBLE
        nameGame_doubleScreen_inverse.visibility = View.INVISIBLE
        game_txt_doubleScreen.visibility = View.INVISIBLE
        game_txt_inverse.visibility = View.INVISIBLE
        counter_doubleScreen.visibility = View.INVISIBLE
        counter_doubleScreen_inverse.visibility = View.INVISIBLE
        line_double_screen.visibility = View.INVISIBLE
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun displayNavigationRule(display: Boolean){
        if (display) {
            next_rule.visibility = View.VISIBLE
            previous_rule.visibility = View.VISIBLE
        } else{
            next_rule.visibility = View.INVISIBLE
            previous_rule.visibility = View.INVISIBLE
        }
    }

    private fun displayRule(rule: String){
        game_txt_alone.text = rule
        game_txt_doubleScreen.text = rule
        game_txt_inverse.text = rule
    }

    private fun getRandom(){
        if (ruleList.size > 4) {
            do {
                random = random(0, ruleList.size-1)
            } while (ruleList[random] == previousRule)
        } else random = random(0, ruleList.size-1)
    }

    private fun play() {
        getRandom()
        displayRule(ruleList[random])
        passedRuleList.add(ruleList[random])
        previousRule = ruleList[random]
        initRuleInfo(ruleList[random])
        ruleList.removeAt(random)
    }

    private fun displayPreviousRule() {
        if (passedRuleCounter > 0) {
            navigationRulePlayerCounter--

            if (navigationRulePlayerCounter < 0) navigationRulePlayerCounter = playerList.size - 1

            displayPlayerName(playerList[navigationRulePlayerCounter].playerName)
            passedRuleCounter--
            displayRule(passedRuleList[passedRuleCounter])
            initRuleInfo(passedRuleList[passedRuleCounter])
            navigationRuleCount--
            displayCounter(navigationRuleCount)
        }
    }

    private fun displayNextRule() {
        if (passedRuleCounter < passedRuleList.size - 1) {
            navigationRulePlayerCounter++

            if (navigationRulePlayerCounter > playerList.size - 1) navigationRulePlayerCounter = 0

            displayPlayerName(playerList[navigationRulePlayerCounter].playerName)
            passedRuleCounter++
            displayRule(passedRuleList[passedRuleCounter])
            initRuleInfo(passedRuleList[passedRuleCounter])
            navigationRuleCount++
            displayCounter(navigationRuleCount)
        } else {
            displayNavigationRule(false)
            displayAlertDialog("LA PARTIE CONTINUE !")
        }
    }

    private fun displayAlertDialog(message: String) {
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

    private fun gameFinish() {
        count = 53
        navigationRuleCount = 53
        info_btn.visibility = View.INVISIBLE
        bottom_btn.visibility = View.VISIBLE
        bottom_btn.text = "Continuer"
        displayRule("Partie fini!")
        displayPlayerName("PAIPIN GAME")
    }

    private fun askBoyAndGirl() {
        AlertDialog.Builder(context!!)
                .setTitle("Mixité ?")
                .setMessage("Êtes vous un groupe mixte ? Juste des garçons, ou juste des filles ?")
                .setPositiveButton("Mixte") { _, _ -> boyAndGirl = true }
                .setNegativeButton("Non Mixte") { _, _ -> boyAndGirl = false }
                .show()
    }

    private fun displayCounter(count: Int) {
        counter.text = "$count/52"
        counter_doubleScreen.text = "$count/52"
        counter_doubleScreen_inverse.text = "$count/52"
    }

    private fun displayPlayerName(name: String) {
        nameGame.text = name
        nameGame_doubleScreen.text = name
        nameGame_doubleScreen_inverse.text = name
    }

    private fun doubleScreen() {
        nameGame.visibility = View.INVISIBLE
        game_txt_alone.visibility = View.INVISIBLE
        counter.visibility = View.INVISIBLE

        nameGame_doubleScreen.visibility = View.VISIBLE
        nameGame_doubleScreen_inverse.visibility = View.VISIBLE
        game_txt_doubleScreen.visibility = View.VISIBLE
        game_txt_inverse.visibility = View.VISIBLE
        counter_doubleScreen.visibility = View.VISIBLE
        counter_doubleScreen_inverse.visibility = View.VISIBLE
        line_double_screen.visibility = View.VISIBLE
    }

    fun playCountDown() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.count_down)
        mediaPlayer.start()
    }

    fun playLeverLesDeuxBras() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.lever_les_deux_bras)
        mediaPlayer.start()
    }

    fun playLeverLaJambeGauche() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.lever_la_jambe_gauche)
        mediaPlayer.start()
    }

    fun playLeverLeBrasDroit() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.lever_le_bras_droit)
        mediaPlayer.start()
    }

    fun playLeverLeBrasGauche() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.lever_le_bras_gauche)
        mediaPlayer.start()
    }

    private fun initRuleInfo(rule: String) {
        bottom_btn.visibility = View.INVISIBLE
        info_btn.visibility = View.VISIBLE

        when (rule) {
            "INVENTE UNE REGLE" -> infoRule = "Le joueur invente une règle. Exemple : Boire de la main gauche, si quelqu'un ne respect pas cette règle il boit une gorgée"
            "DONNE 2 GORGEES" -> info_btn.visibility = View.INVISIBLE
            "BOIS 2 GORGEES" -> info_btn.visibility = View.INVISIBLE
            "DONNE 3 GORGEES" -> info_btn.visibility = View.INVISIBLE
            "BOIS 3 GORGEES" -> info_btn.visibility = View.INVISIBLE
            "LES GARCONS BOIVENT" -> infoRule = "Les garçons boivent 1 gorgée"
            "LES FILLES BOIVENT" -> infoRule = "Les filles boivent 1 gorgée"
            "ACOLYTE" -> infoRule = "Choisisser un joueur qui deviendra votre acolyte, celui-ci deviendra votre partenaire de boisson. A chaque fois que l'un de vous deux bois, l'autre l'accompagne (il ne peut y avoir qu'un seul binôme d'acolyte, si la règle tombe sur un autre joueur lui et son partenaire prendront la place des précédents)"
            "REFERENDUM" -> {
                infoRule = "Désigner du doigt tous en même temps un joueur, le joueur avec le plus de voie boit une gorgée !"
                bottom_btn.visibility = View.VISIBLE
                bottom_btn.text = "GO"
                caseSong = "countdown"
            }
            "RAPIDITE : BRAS GAUCHE" -> {
                infoRule = "Le dernier à lever son bras gauche boit une gorgée"
                playLeverLeBrasGauche()
            }
            "RAPIDITE : BRAS DROIT" -> {
                infoRule = "Le dernier à lever son bras droit boit une gorgée"
                playLeverLeBrasDroit()
            }
            "RAPIDITE : JAMBE GAUCHE" -> {
                infoRule = "Le dernier à lever sa jambe gauche boit une gorgée"
                playLeverLaJambeGauche()
            }
            "RAPIDITE : LES BRAS" -> {
                infoRule = "Le dernier à lever ses deux bras boit une gorgée"
                playLeverLesDeuxBras()
            }
            "CHOISIS UN THEME" -> infoRule = "Le joueur choisi un thème exemple : les fleurs. Ensuite chacun votre tour donner un nom de fleur. Le joueur qui perd est celui qui ne trouve plus de nom, donne un nom déja utilisé ou est trop long à trouver un nom. Le joueur perdant boit une gorgée (le joueur dont c'est le tour commence, le sens de rotation est le même que celui de jeu)"
            "FREEZE" -> infoRule = "Le joueur peut à nimporte quel moment crier FREEZE, je premier joueur à bouger perd et boit une gorgée. Le FREEZE est à usage unique (cligner ou sourire est considéré comme bouger)"
            "PAPIN GAME" -> infoRule = "REGLE DU PAPIN GAME : Le joueur dont c'est le tour commence. Le joueur donne un objet imaginaire à un des autres joueurs en prononçant le mot suivant TIENS, le joueur recevant devra répondre MERCI CEST DE LA PART DE QUI au joueur lui ayant donné l'objet qui lui répondra CEST DE LA PART DE PAPIN il répondra alors MERCI et donnera l'objet à un autre joueur qui répondra à son tout MERCI CEST DE LA PART DE QUI au joueur qui lui a donné qui lui-même demandera au joueur qui lui a donné demandant CEST DE LA PART DE QUI et lui transmettra la réponse (CEST DE LA PART DE PAPIN). le jeu s'arrête lorsqu'un joueur se trompe de phrase, demande CEST DE LA PART DE QUI ou tout autre phrase du jeu au mauvais joueur.  Afin que le jeu soit plus fun ne suivait pas l'ordre chronologique, faite des croisements ou donner l'objet à la personne vous ayant lui-même  donné l'objet ce qui compliquera grandement les choses ! vous pouvez changer le mot PAPIN par le mot que vous voulez (uniquement le joueur qui a lancé le papin décide du mot et peut change quand il veut)"
            "DAME DU REGARD" -> infoRule = "Le joueur devient la dame du regard, chaque joueur regardant la dame du regard dans les yeux boit une gorgée (Le joueur reste la dame du regard jusqu'a ce qu'un autre joueur soit désigné à sa place)"
            "DAME DES QUESTIONS" -> infoRule = "Le joueur devient la dame des questions, chaque joueur répondant à la dame des question dans les yeux boit une gorgée (Le joueur reste la dame des question jusqu'a ce qu'un autre joueur soit désigné à sa place)"
            "ROI DES POUCES" -> infoRule = "Le joueur devient le roi des pouces, le roi des pouces peut poser son pouce où il veut, le dernier à faire de même boit. Pouvoir à usage unique (S'il pose son pouce sur son téléphone, les joueurs peuvent poser leur pouce sur leur propre téléphone mais s'il pose son pouce sur la bouteille d'eau, tous les joueurs doivent poser leur pouce sur la même bouteille)"
            "JUSTE PRIX" -> infoRule = "Le joueur choisit un objet et une caractéristique de son choix, les autres joueurs doivent deviner sa caractéristique, le plus éloigné boit. Ex : La taille de la tour Eiffel"
            "DUEL DE CHI FOU MI" -> infoRule = "Le joueur défi le joueur de son choix, le premier arriver à 3 manche gagnè gagne, c'est donc l'autre joueur qui boit"
        }
    }

    private fun launchMainActivity() {
        val myIntent: Intent = Intent(activity, MainActivity::class.java)
        this.startActivity(myIntent)
    }
}