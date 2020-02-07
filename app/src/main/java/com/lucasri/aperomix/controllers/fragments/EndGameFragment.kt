package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
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
import com.lucasri.aperomix.utils.SharedPref
import com.lucasri.aperomix.view.UserViewModel
import kotlinx.android.synthetic.main.fragment_end_game.*
import java.util.concurrent.Executors
import javax.annotation.meta.When

class EndGameFragment : Fragment() {

    lateinit var userViewModel: UserViewModel
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
            launchMainActivity()
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
        fragment_end_game_GameName.text = "PAPIN GAME"
        fragment_end_game_message.textSize = 60F
        fragment_end_game_message.text = getString(R.string.PapinEndGame)
        gameCounterIncrementation("PAPIN")
    }

    private fun pmuMode(){
        fragment_end_game_GameName.text = "Le PMU"
        fragment_end_game_message.textSize = 20F
        fragment_end_game_message.text = messageToDisplay
        gameCounterIncrementation("PMU")
    }

    private fun beLuckyGameMode(){
        fragment_end_game_GameName.text = "BE LUCKY GAME"
        fragment_end_game_message.textSize = 60F
        fragment_end_game_message.text = getString(R.string.PapinEndGame)
        gameCounterIncrementation("BELUCKY")
    }

    private fun launchMainActivity() {
        val myIntent: Intent = Intent(activity, MainActivity::class.java)
        this.startActivity(myIntent)
    }

}