package com.zali.compatitivegps.presentaion.home.splash.splashlist

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.zali.compatitivegps.R

class FragmentViewPager(var iViewPagerIntractor: IViewPagerIntractor? = null) : PagerAdapter() {


    private lateinit var dots: Array<AppCompatTextView?>

    lateinit var dotLayout : LinearLayout

    private val listImage = listOf<Int>(
        R.drawable.intro_image_one,
        R.drawable.intro_image_tow,
        R.drawable.intro_image_tree
    )

    private val listTitle = listOf<Int>(
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.onboard_layout,container, false)



        val onBoardImage = view.findViewById<AppCompatImageView>(R.id.img_onboard)
        val titleOnboard = view.findViewById<AppCompatTextView>(R.id.txt_title_onboard)
        val descriptionOnboard = view.findViewById<AppCompatTextView>(R.id.txt_description_onboard)
        val txtSkip = view.findViewById<AppCompatTextView>(R.id.btn_skip)
        val txtNext = view.findViewById<AppCompatTextView>(R.id.btn_next)
        dotLayout = view.findViewById(R.id.layout_dot_indicator)


        onBoardImage.setImageResource(listImage[position])
        titleOnboard.setText(listTitle[position])
        descriptionOnboard.setText(listDescription[position])

        txtSkip.setOnClickListener {
            iViewPagerIntractor!!.onSkipClicked(position)
        }

        txtNext.setOnClickListener {
            iViewPagerIntractor!!.onNextClicked(position)
        }



        if (position == 2){
            txtNext.text = "Done"
            txtSkip.visibility = View.GONE
        }

        setUpIndicator(position, container.context)

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun setUpIndicator(position: Int, context: Context){
        dots = Array(3) { AppCompatTextView(context) }
        dotLayout.removeAllViews()

        for (i in 0.. dots.size.minus(1)){
            dots[i] = AppCompatTextView(context)
            dots[i]!!.text = Html.fromHtml("&#8226")
            dots[i]!!.textSize = 35F
            dots[i]!!.setTextColor(context.resources.getColor(R.color.white,context.theme))
            dotLayout.addView(dots[i])
        }
        dots[position]!!.setTextColor(context.resources.getColor(R.color.green,context.theme))
    }
}