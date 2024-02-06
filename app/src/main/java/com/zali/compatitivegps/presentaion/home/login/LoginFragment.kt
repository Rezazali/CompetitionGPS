package com.zali.compatitivegps.presentaion.home.login

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.zali.compatitivegps.R
import com.zali.compatitivegps.databinding.FragmentLoginBinding
class LoginFragment : Fragment() {

    private  val TAG = "LoginFragment"

    private lateinit var binding : FragmentLoginBinding

    private lateinit var owner: LifecycleOwner

    private lateinit var signUpViewModel: SingUpViewModel

    private lateinit var logInViewModel: LogInViewModel

    private lateinit var sendSmsViewModel: SendSmsViewModel

    private lateinit var userActivationViewModel: UserActivationViewModel

    private lateinit var firstEditText : AppCompatEditText


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


        binding.btnSignIn.setOnClickListener {

            addComponents()
        }


        binding.txtSignUp.setOnClickListener {
            binding.cardViewAuth.visibility = View.GONE
            binding.cardViewSms.visibility = View.VISIBLE
        }

        return binding.root

    }


    private fun addComponents() {

        binding.btnSignFacebook.visibility = View.GONE
        binding.edtPassword.visibility = View.GONE

        // First EditText
        firstEditText = createEditText("Dynamic EditText 1",ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        binding.constrainCardViewAuth.addView(firstEditText)
        updateConstraints(binding.constrainCardViewAuth, firstEditText, R.id.edt_emails)

        // Second EditText, below the first one
        val childSecondEditText = createEditText("Dynamic EditText 2",dpToPx(40,requireContext()),dpToPx(40,requireContext()))
        binding.constrainCardViewAuth.addView(childSecondEditText)
        testTow(binding.constrainCardViewAuth,childSecondEditText,firstEditText.id)


        val secondEditText = createEditText("Dynamic EditText 2",dpToPx(0,requireContext()),ConstraintLayout.LayoutParams.WRAP_CONTENT)
        binding.constrainCardViewAuth.addView(secondEditText)
        test(binding.constrainCardViewAuth, secondEditText, childSecondEditText.id,firstEditText.id)


        val thirdEditText = createEditText("Dynamic EditText 1",ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        binding.constrainCardViewAuth.addView(thirdEditText)
        updateConstraints(binding.constrainCardViewAuth, thirdEditText, secondEditText.id)


        val forthEditText = createEditText("Dynamic EditText 1",ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
        binding.constrainCardViewAuth.addView(forthEditText)
        updateConstraints(binding.constrainCardViewAuth, forthEditText, thirdEditText.id)

        updateButtonConstraints(binding.constrainCardViewAuth, binding.btnSignIn, forthEditText.id)

    }

    fun testTow(layout: ConstraintLayout, view: View, anchorViewId: Int) {
        val set = ConstraintSet()
        set.clone(layout)
        set.connect(view.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        set.connect(view.id, ConstraintSet.TOP, anchorViewId, ConstraintSet.BOTTOM, 0)
        set.applyTo(layout)
    }
    private fun test(constrainCardView: ConstraintLayout, view: View, id: Int, secondId : Int) {
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
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            setHintTextColor(Color.WHITE)
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


}