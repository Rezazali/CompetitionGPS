package com.zali.compatitivegps.presentaion.home.login

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tencent.mmkv.MMKV
import com.zali.compatitivegps.R
import com.zali.compatitivegps.databinding.FragmentLoginBinding
import com.zali.compatitivegps.domain.Code
import com.zali.compatitivegps.domain.DataAlertDialog
import com.zali.compatitivegps.domain.LogIn
import com.zali.compatitivegps.domain.SendSms
import com.zali.compatitivegps.domain.SingUp
import com.zali.compatitivegps.util.isNotEmpty
import com.zali.compatitivegps.util.isValidEmail
import com.zali.compatitivegps.util.isValidMobile
import com.zali.compatitivegps.util.textMatches
import com.zali.compatitivegps.util.showToast

class LoginFragment : Fragment() {

    private  val TAG = "LoginFragment"

    private lateinit var binding : FragmentLoginBinding

    private lateinit var owner: LifecycleOwner

    private lateinit var signUpViewModel: SingUpViewModel

    private lateinit var logInViewModel: LogInViewModel

    private lateinit var sendSmsViewModel: SendSmsViewModel

    private lateinit var userActivationViewModel: UserActivationViewModel

    private lateinit var nickNameEditText : AppCompatEditText
    private lateinit var countryCodeEditText : AppCompatEditText
    private lateinit var phoneEditText : AppCompatEditText
    private lateinit var passwordEditText : AppCompatEditText
    private lateinit var repeatPasswordEditText : AppCompatEditText


    private var isSignUpOrLogin : Boolean= true

    private val loginKey by lazy { MMKV.mmkvWithID("loginKey",MMKV.MULTI_PROCESS_MODE) }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = this

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpViewModel = ViewModelProvider(this)[SingUpViewModel::class.java]
        logInViewModel = ViewModelProvider(this)[LogInViewModel::class.java]
        sendSmsViewModel = ViewModelProvider(this)[SendSmsViewModel::class.java]
        userActivationViewModel = ViewModelProvider(this)[UserActivationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)


        binding.btnSignIn.setOnClickListener {
            if (isSignUpOrLogin){
                requestSignIn()
            }else{
                requestSignUp()
            }
        }


        binding.txtSignUp.setOnClickListener {
            isSignUpOrLogin = false
            addComponents()
        }


        return binding.root

    }

    private fun requestSignUp() {
        if (validateInputSignUp(binding.edtEmails,phoneEditText,passwordEditText,repeatPasswordEditText)){
            isSignUpOrLogin = false
            signUpViewModel.requestWebserver(SingUp(binding.edtEmails.text.toString(),nickNameEditText.text.toString(),passwordEditText.text.toString(),phoneEditText.text.toString()))
                .observe(owner){t->
                    if(t.equals("Success")){
                        binding.cardViewAuth.visibility = View.GONE
                        binding.cardViewSms.visibility = View.VISIBLE
                        requestSmsCode()
                    }
                    Log.d(TAG, "requestSignUp: ")
                }
        }
    }

    private fun requestSignIn(){
        if (validateInputSignIn(binding.edtEmails,binding.edtPassword)){
            isSignUpOrLogin = true
            logInViewModel.requestLogin(LogIn(binding.edtEmails.text.toString(),binding.edtPassword.text.toString()))
                .observe(owner){t ->
                    if(t.token.isEmpty()){
                        showAlertDialogButtonClicked(DataAlertDialog(R.string.alert_title_error,R.drawable.circle_alert_red,R.drawable.clear,R.color.red))
                    }else{
                        showAlertDialogButtonClicked(DataAlertDialog(R.string.alert_title_success,R.drawable.circle_alert_green,R.drawable.cheak,R.color.green))
                        loginKey.putInt("loginKey",1)
                        goHome()
                    }
                    Log.d(TAG, "requestSignIn: ")
                }
        }

    }

    private fun requestSmsCode() {
        sendSmsViewModel.requestSmsSend(SendSms(countryCodeEditText.text.toString(),phoneEditText.text.toString()))
            .observe(owner){t->
                if (t.content.equals("ok")){
                    binding.txtSendCodeAgain.visibility = View.GONE
                    edtCodeWatcher()
                    startTimer(10000)
                }
                Log.d(TAG, "requestSmsCode: ")
            }
    }

    private fun requestVerifyCodeSms() {
        val code = binding.inputOne.text.toString() +
                binding.inputTow.text.toString() +
                binding.inputTree.text.toString() +
                binding.inputFour.text.toString()

        userActivationViewModel.requestUserActivation(Code(code))
            .observe(owner){t->
                loginKey.putInt("loginKey",1)
                goHome()
                Log.d(TAG, "requestVerifyCodeSms: ")
            }
    }



    private fun edtCodeWatcher(){

        val codeWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (binding.inputOne.text!!.isNotEmpty() &&
                    binding.inputTow.text!!.isNotEmpty() &&
                    binding.inputTree.text!!.isNotEmpty() &&
                    binding.inputFour.text!!.isNotEmpty()) {

                    requestVerifyCodeSms()
                }
            }
        }


        binding.inputOne.addTextChangedListener(codeWatcher)
        binding.inputTow.addTextChangedListener(codeWatcher)
        binding.inputTree.addTextChangedListener(codeWatcher)
        binding.inputFour.addTextChangedListener(codeWatcher)
    }


    private fun addComponents() {

        binding.btnSignFacebook.visibility = View.GONE
        binding.edtPassword.visibility = View.GONE
        val tint = Color.parseColor("#03DAC6")
        binding.edtEmails.backgroundTintList = ColorStateList.valueOf(tint)


        nickNameEditText = createEditText("NickName",ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        binding.constrainCardViewAuth.addView(nickNameEditText)
        updateConstraints(binding.constrainCardViewAuth, nickNameEditText, R.id.edt_emails)


        countryCodeEditText = createEditText("+Code",dpToPx(45,requireContext()),dpToPx(45,requireContext()))
        binding.constrainCardViewAuth.addView(countryCodeEditText)
        countryCodConstraint(binding.constrainCardViewAuth,countryCodeEditText,nickNameEditText.id)


        phoneEditText = createEditText("Phone",dpToPx(0,requireContext()),ConstraintLayout.LayoutParams.WRAP_CONTENT)
        binding.constrainCardViewAuth.addView(phoneEditText)
        phoneConstraint(binding.constrainCardViewAuth, phoneEditText, countryCodeEditText.id,nickNameEditText.id)


        passwordEditText = createEditText("Password",ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        binding.constrainCardViewAuth.addView(passwordEditText)
        updateConstraints(binding.constrainCardViewAuth, passwordEditText, phoneEditText.id)


        repeatPasswordEditText = createEditText("Repeat Password",ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        binding.constrainCardViewAuth.addView(repeatPasswordEditText)
        updateConstraints(binding.constrainCardViewAuth, repeatPasswordEditText, passwordEditText.id)

        updateButtonConstraints(binding.constrainCardViewAuth, binding.btnSignIn, repeatPasswordEditText.id)

    }

    private fun countryCodConstraint(layout: ConstraintLayout, view: View, anchorViewId: Int) {
        val set = ConstraintSet()
        set.clone(layout)
        set.connect(view.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        set.connect(view.id, ConstraintSet.TOP, anchorViewId, ConstraintSet.BOTTOM,  (8 * resources.displayMetrics.density).toInt())
        set.applyTo(layout)
    }
    private fun phoneConstraint(constrainCardView: ConstraintLayout, view: View, id: Int, secondId : Int) {
        val set = ConstraintSet()
        set.clone(constrainCardView)
        set.connect(view.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
        set.connect(view.id, ConstraintSet.START, id, ConstraintSet.END)
        set.connect(view.id, ConstraintSet.TOP, secondId, ConstraintSet.BOTTOM)
        set.applyTo(constrainCardView)
    }

    private fun createEditText(hintText: String, width : Int, height : Int): AppCompatEditText {
        return AppCompatEditText(requireContext()).apply {
            id = View.generateViewId()
            hint = hintText
            layoutParams = ConstraintLayout.LayoutParams(
                width,
                height
            ).apply {
                topMargin = (8 * resources.displayMetrics.density).toInt()
            }
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            setHintTextColor(Color.GRAY)
            val tint = Color.parseColor("#03DAC6")
            backgroundTintList = ColorStateList.valueOf(tint)
        }

    }

    private fun updateConstraints(layout: ConstraintLayout, view: View, anchorViewId: Int) {
        val set = ConstraintSet()
        set.clone(layout)
        set.connect(view.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        set.connect(view.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
        set.connect(view.id, ConstraintSet.TOP, anchorViewId, ConstraintSet.BOTTOM, (8 * resources.displayMetrics.density).toInt())
        set.applyTo(layout)
    }


    private fun updateButtonConstraints(layout: ConstraintLayout, button: AppCompatButton, anchorViewId: Int) {
        val set = ConstraintSet()
        set.clone(layout)
        set.connect(button.id, ConstraintSet.TOP, anchorViewId, ConstraintSet.BOTTOM, (8 * resources.displayMetrics.density).toInt())
        set.applyTo(layout)
    }


    private fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }


    private fun showAlertDialogButtonClicked(dataAlertDialog: DataAlertDialog) {

        val builder = AlertDialog.Builder(requireContext())

        val view = layoutInflater.inflate(R.layout.dialog_alert, null)
        val txtTitle = view.findViewById<AppCompatTextView>(R.id.title_alert)
        val txtOk = view.findViewById<AppCompatTextView>(R.id.txt_finish_alert)

        val imgCircle = view.findViewById<AppCompatImageView>(R.id.img_alert_circle)
        val imgStateError = view.findViewById<AppCompatImageView>(R.id.img_state_error)
        builder.setView(view)
        val dialog = builder.create()


        txtTitle.setText(dataAlertDialog.txtTitle)

        txtTitle.setTextColor(resources.getColor(dataAlertDialog.color))
        txtOk.setTextColor(resources.getColor(dataAlertDialog.color))

        imgCircle.setImageResource(dataAlertDialog.imgCircle)
        imgStateError.setImageResource(dataAlertDialog.imgStateError)


        txtOk.setOnClickListener {
            dialog.cancel()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }

    private fun goHome(){
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }


    private fun startTimer(timeInMillis: Long) {
        object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the timer text view every second
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding.txtTimeNumber.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                // Enable the resend button when the timer finishes
                binding.txtSendCodeAgain.visibility = View.VISIBLE
                binding.txtTimeNumber.text = "00:00"

                binding.txtSendCodeAgain.setOnClickListener {
                    // Code to resend the SMS
                    requestSmsCode()
                    binding.txtSendCodeAgain.visibility = View.GONE
                    startTimer(10000) // Restart the timer
                }
            }
        }.start()
    }


    private fun validateInputSignUp(emailEditText: AppCompatEditText, mobileEditText: AppCompatEditText, passwordEditText: AppCompatEditText, repeatPasswordEditText: AppCompatEditText): Boolean {
        if (!emailEditText.isNotEmpty() || !mobileEditText.isNotEmpty() || !passwordEditText.isNotEmpty() || !repeatPasswordEditText.isNotEmpty()) {
            Toast.makeText(requireContext(), "Edit text Empty field", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!emailEditText.isValidEmail()){
            requireContext().showToast("Bad Email")
            redLineEditText(emailEditText)
            return false
        }
        if (!passwordEditText.textMatches(repeatPasswordEditText)) {
            requireContext().showToast("password dont match")
            redLineEditText(passwordEditText)
            return false
        }
        if (!mobileEditText.isValidMobile()) {
            requireContext().showToast("Bad mobail")
            redLineEditText(mobileEditText)
            return false
        }

        if (!passwordEditText.textMatches(repeatPasswordEditText)) {
            redLineEditText(passwordEditText)
            return false
        }

        return true
    }

    private fun validateInputSignIn(emailEditText: AppCompatEditText, passwordEditText: AppCompatEditText): Boolean{
        if (!emailEditText.isNotEmpty() || !passwordEditText.isNotEmpty()) {
            requireContext().showToast("Edit text Empty field")
            return false
        }
        if (!emailEditText.isValidEmail()) {
            requireContext().showToast("Bad Email")
            redLineEditText(emailEditText)
            return false
        }
        return true
    }



    private fun redLineEditText(editText: AppCompatEditText){
        ViewCompat.setBackgroundTintList(
            editText,
            ContextCompat.getColorStateList(requireContext(), R.color.red)
        )
    }

}