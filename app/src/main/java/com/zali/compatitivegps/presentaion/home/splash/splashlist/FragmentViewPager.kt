package com.zali.compatitivegps.presentaion.home.splash.splashlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.zali.compatitivegps.R

class FragmentViewPager() : PagerAdapter() {

    val listImage = listOf<Int>(
        R.drawable.intro_image_one,
        R.drawable.intro_image_tow,
        R.drawable.intro_image_tree
    )

    val listTitle = listOf<Int>(
        R.string.title_onboard_one,
        R.string.title_onboard_tow,
        R.string.title_onboard_trre
    )

    val listDescription = listOf<Int>(
        R.string.description_onboard_one,
        R.string.description_onboard_tow,
        R.string.description_onboard_trre
    )

    override fun getCount(): Int {
        return listTitle.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view  == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.onboard_layout,container, false)



        val onBoardImage = view.findViewById<AppCompatImageView>(R.id.img_onboard)
        val titleOnboard = view.findViewById<AppCompatTextView>(R.id.txt_title_onboard)
        val descriptionOnboard = view.findViewById<AppCompatTextView>(R.id.txt_description_onboard)


        onBoardImage.setImageResource(listImage[position])
        titleOnboard.text = listTitle[position].toString()
        descriptionOnboard.text = listDescription[position].toString()

        container.addView(view)



        return view
    }
}