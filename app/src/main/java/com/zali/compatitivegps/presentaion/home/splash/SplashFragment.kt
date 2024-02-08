package com.zali.compatitivegps.presentaion.home.splash

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.tencent.mmkv.MMKV
import com.zali.compatitivegps.R
import com.zali.compatitivegps.databinding.FragmentSplashBinding
import com.zali.compatitivegps.presentaion.home.splash.splashlist.FragmentViewPager
import com.zali.compatitivegps.presentaion.home.splash.splashlist.IViewPagerIntractor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() , IViewPagerIntractor{

    private val TAG = "SplashFragment"

    private lateinit var binding : FragmentSplashBinding

    private lateinit var viewPager : FragmentViewPager

    private val onBoardKey by lazy{MMKV.mmkvWithID("onBord",MMKV.MULTI_PROCESS_MODE)}

    private val loginKey by lazy{MMKV.mmkvWithID("loginKey",MMKV.MULTI_PROCESS_MODE)}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        binding = FragmentSplashBinding.inflate(layoutInflater)


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")

        val keyLogin = loginKey.decodeInt("loginKey")
        val key = onBoardKey.decodeInt("onBord")
        if (keyLogin == 1){
            goToMainPage()
        }else if (key == 0){
            binding.logoSplash.visibility = View.GONE
            binding.rootSplash.setBackgroundResource(R.drawable.bacground_login)
            viewPager = FragmentViewPager(this)
            binding.viewPager.adapter = viewPager
        } else{
            binding.logoSplash.visibility = View.VISIBLE
            binding.viewPager.visibility = View.GONE
            binding.rootSplash.setBackgroundResource(R.drawable.bacground_splash)

            lifecycleScope.launch {
                delay(2000)
                goToLoginPage()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)



    private fun getItem(i: Int): Int {
        return binding.viewPager.currentItem + i
    }


    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }


    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onSkipClicked(position: Int) {
        goToLoginPage()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onNextClicked(position: Int) {
        if(getItem(0) < 2){
            binding.viewPager.setCurrentItem(getItem(1), true)
        }else{
            onBoardKey.putInt("onBord",1)
            goToLoginPage()
        }
    }


    private fun goToLoginPage(){
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }


    private fun goToMainPage(){
        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
    }
}