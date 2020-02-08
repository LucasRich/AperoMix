package com.lucasri.aperomix.controllers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.activities.MainActivity
import com.lucasri.aperomix.controllers.activities.MainActivity.Companion.afterGame
import com.lucasri.aperomix.database.injection.UserViewModelFactory
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.utils.launchActivity
import com.lucasri.aperomix.view.UserViewModel
import kotlinx.android.synthetic.main.fragment_end_game.*
import java.util.concurrent.Executors

class EndGameFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    lateinit var auth: FirebaseAuth

    companion object{
        var launchMode: String? = null
        var messageToDisplay: String? = null

        var ID_PAPIN_MODE = "PAPIN_MODE"
        var ID_PMU_MODE = "PMU_MODE"
        var ID_BELUCKY_MODE = "BELUCKY_MODE"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_end_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureUserViewModel()
        auth = FirebaseAuth.getInstance()

        when(launchMode){
            ID_PAPIN_MODE -> papinGameMode()
            ID_PMU_MODE -> pmuMode()
            ID_BELUCKY_MODE -> beLuckyGameMode()
        }

        fragment_end_game_btn.setOnClickListener {
            afterGame = true
            context!!.launchActivity(MainActivity())
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureUserViewModel() {
        val userViewModelFactory = UserViewModelFactory(UserDataRepository(), Executors.newSingleThreadExecutor())
        this.userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun gameCounterIncrementation(game: String){
        if (auth.currentUser != null){
            userViewModel.getUser(auth.currentUser!!.uid).addOnSuccessListener {documentSnapshot ->
                when(game){
                    "PAPIN" -> userViewModel.updatePapinGameCounter(documentSnapshot.get("papinGameCounter").toString().toInt() + 1, auth.currentUser!!.uid)
                    "PMU" -> userViewModel.updatePmuGameCounter(documentSnapshot.get("pmuGameCounter").toString().toInt() + 1, auth.currentUser!!.uid)
                    "BELUCKY" -> userViewModel.updateBeLuckyGameCounter(documentSnapshot.get("beLuckyGameCounter").toString().toInt() + 1, auth.currentUser!!.uid)
                }
            }
        }
    }

    private fun papinGameMode(){
        //Init views
        fragment_end_game_GameName.text = getString(R.string.endGame_gameName_papin)
        fragment_end_game_message.textSize = 60F
        fragment_end_game_message.text = getString(R.string.PapinEndGame)

        //Increment game counter
        gameCounterIncrementation("PAPIN")
    }

    private fun pmuMode(){
        //Init views
        fragment_end_game_GameName.text = getString(R.string.endGame_gameName_PMU)
        fragment_end_game_message.textSize = 20F
        fragment_end_game_message.text = messageToDisplay

        //Increment game counter
        gameCounterIncrementation("PMU")
    }

    private fun beLuckyGameMode(){
        //Init views
        fragment_end_game_GameName.text = getString(R.string.endGame_gameName_BeLucky)
        fragment_end_game_message.textSize = 60F
        fragment_end_game_message.text = getString(R.string.PapinEndGame)

        //Increment game counter
        gameCounterIncrementation("BELUCKY")
    }
}