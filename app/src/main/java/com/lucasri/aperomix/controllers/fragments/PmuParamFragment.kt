package com.lucasri.aperomix.controllers.fragments

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.MainFragment
import com.lucasri.aperomix.controllers.fragments.PmuGameFragment
import com.lucasri.aperomix.view.adapter.PmuParamAdapter
import kotlinx.android.synthetic.main.fragment_pmu_param.*
import kotlinx.android.synthetic.main.info_dialog.view.*

class PmuParamFragment : Fragment() {

    private var adapter: PmuParamAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.configureRecyclerView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_pmu_param, container, false)
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureRecyclerView() {
        //INIT
        this.adapter = PmuParamAdapter(MainFragment.playerList)
        this.pmu_param_recycler_view.adapter = this.adapter
        this.pmu_param_recycler_view.layoutManager = LinearLayoutManager(context)

        //ANIM
        val controller = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation_fall_down)
        this.pmu_param_recycler_view.layoutAnimation = controller
        this.pmu_param_recycler_view.scheduleLayoutAnimation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_pmu_param_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.done -> launchFragmentPmuGame()
            R.id.rule -> displayRule()

        }
        return super.onOptionsItemSelected(item)
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun displayRule() {
        val dialogBuilder = AlertDialog.Builder(context!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.info_dialog, null)
        dialogBuilder.setView(dialogView)

        //INIT
        dialogView.text_info.text = getString(R.string.PmuParamRule)

        //DISPLAY DIALOG
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun launchFragmentPmuGame() {
        val pmuGameFragment = PmuGameFragment()
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_game_container_frame, pmuGameFragment, "findThisFragment")
                .addToBackStack(null)
                .commit()
    }

    private fun clearPlayerListNbDrink(){
        for (player in MainFragment.playerList){
            player.caroNbDrink = 0
            player.coeurNbDrink = 0
            player.trefleNbDrink = 0
            player.picNbDrink = 0
        }
    }

    override fun onStart() {
        super.onStart()
        clearPlayerListNbDrink()
    }
}
