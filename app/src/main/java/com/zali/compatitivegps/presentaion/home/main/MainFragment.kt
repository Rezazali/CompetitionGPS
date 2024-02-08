package com.zali.compatitivegps.presentaion.home.main

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigationrail.NavigationRailView
import com.zali.compatitivegps.R
import com.zali.compatitivegps.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        val imageViewIds = arrayOf(
            R.id.img_gps_sid_bar,
            R.id.img_key_side_bar,
            R.id.img_chat_side_bar,
            R.id.img_favorite_side_bar,
            R.id.img_cup_side_bar,
            R.id.img_open
        )

        binding.imgOpen.setOnClickListener {
            imageViewIds.forEach { imageViewId ->
                val imageView = requireView().findViewById<AppCompatImageView>(imageViewId)
                val layoutParams = imageView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET
                imageView.layoutParams = layoutParams
            }
            binding.txtGpsSidBar.visibility = View.VISIBLE
            binding.txtChatSideBar.visibility = View.VISIBLE
            binding.txtFavoriteSideBar.visibility = View.VISIBLE
            binding.txtKeySideBar.visibility = View.VISIBLE
            binding.txtCupSideBar.visibility = View.VISIBLE
        }



        return binding.root
    }


}