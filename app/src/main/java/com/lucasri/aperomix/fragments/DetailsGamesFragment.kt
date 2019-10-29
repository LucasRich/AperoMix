package com.lucasri.aperomix.fragments

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.os.Bundle
import com.lucasri.aperomix.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import kotlinx.android.synthetic.main.fragment_details_games.*


class DetailsGamesFragment : BottomSheetDialogFragment(){

    private lateinit var gameName : String

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
        fragment_details_games_txt.text = gameName
    }

}
