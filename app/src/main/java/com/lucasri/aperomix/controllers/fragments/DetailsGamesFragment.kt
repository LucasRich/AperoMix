package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.os.Bundle
import com.lucasri.aperomix.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasri.aperomix.controllers.activities.GameContainer
import com.lucasri.aperomix.view.adapter.GamesDetailsAdapter
import kotlinx.android.synthetic.main.fragment_details_games.*


class DetailsGamesFragment : BottomSheetDialogFragment(){

    private lateinit var gameName : String
    private var adapter: GamesDetailsAdapter? = null
    private val pictureList = ArrayList<Int>()

    // FOR CONSTRUCTING
    companion object {

        fun newInstance(gameName: String?): DetailsGamesFragment {
            val bottomSheetFragment = DetailsGamesFragment()
            val bundle = Bundle()
            bundle.putString("KEY_GAME_NAME", gameName!!)
            bottomSheetFragment.arguments = bundle
            return bottomSheetFragment
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details_games, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        gameName = arguments!!.getString("KEY_GAME_NAME")!!
        fragment_details_games_title.text = gameName

        initPictureList()
        configureRecyclerView()

        fragment_details_games_playBtn.setOnClickListener {
            launchGame(gameName, view)
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureRecyclerView() {
        //INIT
        this.adapter = GamesDetailsAdapter(pictureList)
        this.fragment_details_games_recyclerView.adapter = this.adapter
        this.fragment_details_games_recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun initPictureList(){
        pictureList.add(R.drawable.img_papigame_1)
        pictureList.add(R.drawable.img_papigame_2)
        pictureList.add(R.drawable.img_papigame_3)
        pictureList.add(R.drawable.img_papigame_4)
        pictureList.add(R.drawable.img_papigame_5)
    }

    private fun launchGame(gameTitle: String, viewClicked: View) {

        val myIntent = Intent(context, GameContainer::class.java)
        val bundle = Bundle()

        bundle.putString("startParameter", gameTitle)
        myIntent.putExtras(bundle)

        startActivity(myIntent)
    }

}
