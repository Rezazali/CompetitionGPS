package com.zali.compatitivegps.presentaion.home.splash

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.zali.compatitivegps.R
import com.zali.compatitivegps.databinding.FragmentSplashBinding
import com.zali.compatitivegps.presentaion.home.splash.splashlist.FragmentViewPager
import kotlinx.coroutines.delay


class SplashFragment : Fragment() {

    private lateinit var binding : FragmentSplashBinding

    private lateinit var viewPager : FragmentViewPager

    private lateinit var linearLayout: LinearLayout

    private lateinit var buttonSkip : AppCompatButton
    private lateinit var buttonNext : AppCompatButton

    private lateinit var dots: Array<AppCompatTextView?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(layoutInflater)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val test = layoutInflater.inflate(R.layout.onboard_layout, null)
        linearLayout = test.findViewById(R.id.layout_dot_indicator)
        buttonSkip = test.findViewById(R.id.btn_skip)
        buttonNext = test.findViewById(R.id.btn_next)


        viewPager = FragmentViewPager()

        binding.viewPager.adapter = viewPager

        binding.viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                setUpIndicator(position)

                if (position > 0){

                    buttonSkip.setVisibility(View.VISIBLE)

                }else {

                    buttonSkip.setVisibility(View.INVISIBLE)

                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }


    private suspend fun changeBacgroudColor(){

        delay(3000)
      /*  binding.logo.visibility = View.GONE
        binding.rootSplash.setBackgroundResource(R.drawable.bacground_login)*/
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setUpIndicator(position: Int){
        dots = arrayOf(AppCompatTextView(requireContext()))
        linearLayout.removeAllViews()

        for (i in 0.. dots.size){
            dots[i] = AppCompatTextView(requireContext())
            dots[i]!!.text = Html.fromHtml("&#8226")
            dots[i]!!.textSize = 35F
            dots[i]!!.setTextColor(resources.getColor(R.color.black,requireContext().theme))
        }


        dots[position]?.setTextColor(resources.getColor(R.color.white,requireContext().theme))
    }


    private fun getitem(i: Int): Int {
        return binding.viewPager.currentItem + i
    }
}