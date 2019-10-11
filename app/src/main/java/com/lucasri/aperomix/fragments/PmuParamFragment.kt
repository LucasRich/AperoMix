package com.lucasri.aperomix.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasri.aperomix.R
import com.lucasri.aperomix.view.adapter.PlayerAdapter
import com.lucasri.aperomix.view.adapter.PmuParamAdapter
import com.lucasri.aperomix.model.Player
import com.lucasri.aperomix.view.adapter.MainFragmentAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_pmu_param.*
import kotlinx.android.synthetic.main.info_dialog.view.*
import java.util.*

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
        this.adapter = PmuParamAdapter(MainFragment.playerList)
        this.pmu_param_recycler_view.adapter = this.adapter
        this.pmu_param_recycler_view.layoutManager = LinearLayoutManager(context)
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
        dialogView.text_info.text = "Que faire ?\n\nCe jeu est un jeu de hasard. Miser le nombre de gorgées de votre choix sur la carte de votre choix. Une fois misé, buvez le nombre de gorgées que vous avez misé. Ensuite cliquez en haut à droite et laissez le jeu faire, vous découvrirez si vous avez misé sur les bons chevaux !"

        //DISPLAY DIALOG
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun launchFragmentPmuGame() {
        val pmuGameFragment = PmuGameFragment()
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_game_frame, pmuGameFragment, "findThisFragment")
                .addToBackStack(null)
                .commit()
    }
}
