package com.lucasri.aperomix.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.View.adapter.BeLuckyGameAdapter
import com.lucasri.aperomix.View.adapter.BeLuckyParamAdapter
import com.lucasri.aperomix.View.adapter.PlayerAdapter
import com.lucasri.aperomix.model.Player
import kotlinx.android.synthetic.main.be_lucky_case.view.*
import kotlinx.android.synthetic.main.fragment_be_lucky_game.*
import java.util.*

class BeLuckyGameFragment : Fragment(){

    private val playerList = ArrayList<Player>()
    private var beLuckyGameAdapter: BeLuckyGameAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_be_lucky_game, container, false)
        setHasOptionsMenu(true)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        this.initPlayerList()

        case1.position1.setBackgroundColor(ContextCompat.getColor(context!!, R.color.design_default_color_primary))
        case1.position2.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPlayerList() {
        playerList.addAll(BeLuckyParamAdapter.playerList)

        beLuckyGameAdapter = BeLuckyGameAdapter(context, playerList)
        listPlayer!!.adapter = beLuckyGameAdapter
    }

    // ---------------------
    // UTILS
    // ---------------------
}