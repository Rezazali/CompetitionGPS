package com.zali.compatitivegps.presentaion.home.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.zali.compatitivegps.R
import com.zali.compatitivegps.databinding.FragmentLoginBinding
import com.zali.compatitivegps.domain.LogIn
import com.zali.compatitivegps.domain.SendSms
import com.zali.compatitivegps.domain.SingUp


class LoginFragment : Fragment() {

    private  val TAG = "LoginFragment"

    private lateinit var binding : FragmentLoginBinding

    private lateinit var owner: LifecycleOwner

    private lateinit var signUpViewModel: SingUpViewModel

    private lateinit var logInViewModel: LogInViewModel

    private lateinit var sendSmsViewModel: SendSmsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)



        signUpViewModel = ViewModelProvider(this)[SingUpViewModel::class.java]
        logInViewModel = ViewModelProvider(this)[LogInViewModel::class.java]
        sendSmsViewModel = ViewModelProvider(this)[SendSmsViewModel::class.java]



  /*      logInViewModel.requestLogin(LogIn("tests@gmail.com","string"))
            .observe(owner){t ->
                var test = t
                Log.d(TAG, "onCreateView: ".plus(t))
            }*/

        sendSmsViewModel.requestSmsSend(SendSms("+971","6666666666"))
            .observe(owner){ t ->
                var test = t
                Log.d(TAG, "onCreateView: ")
            }

/*
        signUpViewModel.requestWebserver(SingUp("tesxcvcxvcxt@gmail.com","rubin","khfkdj","54654654564"))
            .observe(owner){t ->

                var test = t
                Log.d(TAG, "onCreateView: ")
            }
*/




        return binding.root
    }

}