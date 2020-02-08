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
import com.lucasri.aperomix.controllers.activities.GameContainerActivity
import com.lucasri.aperomix.controllers.activities.GameContainerActivity.Companion.launchMode
import com.lucasri.aperomix.utils.launchActivity
import com.lucasri.aperomix.view.adapter.GameDetailsAdapter
import kotlinx.android.synthetic.main.fragment_game_details.*

class GameDetailsFragment : BottomSheetDialogFragment(){

    private var adapter: GameDetailsAdapter? = null

    companion object {
        var gameName : String? = null
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        initView()

        fragment_details_games_playBtn.setOnClickListener {
            launchMode = gameName
            context!!.launchActivity(GameContainerActivity())
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureRecyclerView(pictureList: ArrayList<Int>) {
        //INIT
        this.adapter = GameDetailsAdapter(pictureList)
        this.fragment_details_games_recyclerView.adapter = this.adapter
        this.fragment_details_games_recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun initView(){
        fragment_details_games_title.text = gameName

        when(gameName){
            "Papin Game" -> papinGameMode()
            "Le PMU" -> pmuGameMode()
            "Be lucky Game" -> beLuckyGameMode()
        }
    }

    private fun beLuckyGameMode(){
        //Init views
        fragment_details_games_nbPlayer.text = getString(R.string.GameDetails_nbPlayer_papin)
        fragment_details_games_duration.text = getString(R.string.GameDetails_duration_papin)
        configureRecyclerView(getBeLuckyPictureList())
        fragment_details_games_description.text = getString(R.string.Belucky_description_content)
    }

    private fun papinGameMode(){
        //Init views
        fragment_details_games_nbPlayer.text = getString(R.string.GameDetails_nbPlayer_papin)
        fragment_details_games_duration.text = getString(R.string.GameDetails_duration_papin)
        configureRecyclerView(getPapinPictureList())
        fragment_details_games_description.text = getString(R.string.GameDetails_desc_papin)
    }

    private fun pmuGameMode(){
        //Init views
        fragment_details_games_nbPlayer.text = getString(R.string.GameDetails_nbPlayer_pmu)
        fragment_details_games_duration.text = getString(R.string.GameDetails_duration_pmu)
        configureRecyclerView(getPmuPictureList())
        fragment_details_games_description.text = getString(R.string.GameDetails_desc_pmu)
    }

    private fun getPapinPictureList(): ArrayList<Int>{
        //Init pictures list
        val pictureList = ArrayList<Int>()
        pictureList.add(R.drawable.img_papin_1)
        pictureList.add(R.drawable.img_papin_2)
        pictureList.add(R.drawable.img_papin_3)
        pictureList.add(R.drawable.img_papin_4)
        pictureList.add(R.drawable.img_papin_5)

        return pictureList
    }

    private fun getPmuPictureList(): ArrayList<Int>{
        //Init pictures list
        val pictureList = ArrayList<Int>()
        pictureList.add(R.drawable.img_pmu_1)
        pictureList.add(R.drawable.img_pmu_2)
        pictureList.add(R.drawable.img_pmu_3)
        pictureList.add(R.drawable.img_pmu_4)
        pictureList.add(R.drawable.img_pmu_5)
        pictureList.add(R.drawable.img_pmu_6)

        return pictureList
    }

    private fun getBeLuckyPictureList(): ArrayList<Int>{
        //Init pictures list
        val pictureList = ArrayList<Int>()
        pictureList.add(R.drawable.img_belucky_1)
        pictureList.add(R.drawable.img_belucky_2)
        pictureList.add(R.drawable.img_belucky_3)
        pictureList.add(R.drawable.img_belucky_4)
        pictureList.add(R.drawable.img_belucky_5)
        pictureList.add(R.drawable.img_belucky_6)

        return pictureList
    }
}
