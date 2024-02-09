package com.zali.compatitivegps.presentaion.home.main

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigationrail.NavigationRailView
import com.zali.compatitivegps.R
import com.zali.compatitivegps.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private  val TAG = "MainFragment"

    private lateinit var binding: FragmentMainBinding

    private lateinit var owner: LifecycleOwner

    private lateinit var randomTaskViewModel: RandomTaskViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomTaskViewModel = ViewModelProvider(this)[RandomTaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)


        randomTaskViewModel.requestRandomTask("77e4c9d3-c678-682d-4aab-0b60564a15af",1)
            .observe(owner){t->
                val test = t
                Log.d(TAG, "onCreateView: ")
            }

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


        binding.cardViewNoTask.setOnClickListener {
            test()
        }



        return binding.root
    }

    fun test(){
        binding.cardViewNoTask.visibility = View.GONE
            // Make card_view_no_task invisible
            binding.cardViewWordSkill.visibility = View.VISIBLE
            binding.imgTest.visibility = View.VISIBLE

            // Prepare the ConstraintSet for adjustments
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.rootLayout)

            // Adjust card_view_country to take up 40% of the screen
            constraintSet.connect(R.id.card_view_country, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
            constraintSet.connect(R.id.card_view_country, ConstraintSet.BOTTOM, R.id.guideline40, ConstraintSet.TOP,0)
            constraintSet.setGuidelinePercent(R.id.guideline40, 0.40f) // Adjust guideline to 40%

            // Make card_view_word_skill and card_view_task visible


            // Adjust card_view_word_skill to be below card_view_country and take up 20% space
            constraintSet.connect(R.id.card_view_word_skill, ConstraintSet.TOP, R.id.guideline40, ConstraintSet.BOTTOM, 0)
            constraintSet.connect(R.id.card_view_word_skill, ConstraintSet.BOTTOM, R.id.guideline80, ConstraintSet.TOP, 0)
            constraintSet.connect(R.id.card_view_word_skill, ConstraintSet.END, R.id.view_balance_page, ConstraintSet.END, 0)
            constraintSet.connect(R.id.card_view_word_skill, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
            constraintSet.setGuidelinePercent(R.id.guideline80, 0.60f) // Adjust guideline to mark next 20%

            // Adjust card_view_task to take up remaining 40% of the screen
            constraintSet.connect(R.id.img_test, ConstraintSet.TOP, R.id.guideline80, ConstraintSet.BOTTOM, 0)
            constraintSet.connect(R.id.img_test, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
            constraintSet.connect(R.id.img_test, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
            constraintSet.connect(R.id.img_test, ConstraintSet.END, R.id.view_balance_page, ConstraintSet.END, 0)

            // Apply all constraints
            constraintSet.applyTo(binding.rootLayout)
    }


}