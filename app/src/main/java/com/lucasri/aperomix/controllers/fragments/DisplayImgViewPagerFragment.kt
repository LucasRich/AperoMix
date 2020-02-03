package com.lucasri.aperomix.controllers.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import com.lucasri.aperomix.R
import kotlinx.android.synthetic.main.fragment_display_img_view_pager.*


class DisplayImgViewPagerFragment : Fragment() {

    private val imgList = listOf(R.drawable.explain_papin_game_rule1, R.drawable.explain_papin_game_rule2, R.drawable.explain_papin_game_rule3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_display_img_view_pager, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        val position = arguments!!.getInt(KEY_POSITION, -1)

        fragment_display_image_view_pager_img.setImageResource(imgList[position])
    }

    companion object {
        private val KEY_POSITION = "position"

        fun newInstance(position: Int): DisplayImgViewPagerFragment {
            val frag = DisplayImgViewPagerFragment()
            val args = Bundle()
            args.putInt(KEY_POSITION, position)
            frag.arguments = args
            return frag
        }
    }
}