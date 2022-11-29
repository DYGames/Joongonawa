package com.team7.joongonawa


import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_signup_informagree.*


class SignUpInformAgree : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_informagree)

        allCheckBox.setOnClickListener { onCheckChanged(allCheckBox) }
        firstCheckBox.setOnClickListener { onCheckChanged(firstCheckBox) }
        secondCheckBox.setOnClickListener { onCheckChanged(secondCheckBox) }
        thirdCheckBox.setOnClickListener { onCheckChanged(thirdCheckBox) }
        fourthCheckBox.setOnClickListener { onCheckChanged(thirdCheckBox) }


        btnfirstAgree.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        btnsecondAgree.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        btnthirdAgree.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        btnfirstAgree.setOnClickListener{
            if(firstAgree.isVisible){
                firstAgree.visibility = View.GONE
            }
            else{
                firstAgree.visibility = View.VISIBLE
                secondAgree.visibility = View.GONE
                thirdAgree.visibility = View.GONE

            }
        }
        btnsecondAgree.setOnClickListener{
            if(secondAgree.isVisible){
                secondAgree.visibility = View.GONE
            }
            else{
                firstAgree.visibility = View.GONE
                secondAgree.visibility = View.VISIBLE
                thirdAgree.visibility = View.GONE

            }
        }
        btnthirdAgree.setOnClickListener{
            if(thirdAgree.isVisible){
                thirdAgree.visibility = View.GONE
            }
            else{
                firstAgree.visibility = View.GONE
                secondAgree.visibility = View.GONE
                thirdAgree.visibility = View.VISIBLE
            }
        }


        btnGoToUserInfo.setOnClickListener {
            if(!isAllChecked()){
                allCheckBox.error = "약관에 모두 동의해주세요"
            }
            else {

                var intentInformAgreeToUserInfo = Intent(this, SignUpUserInfo::class.java)
//                intentInformAgreeToUserInfo.putExtra("E-mail", EmailEdit.text.toString())
//                intentInformAgreeToUserInfo.putExtra("PhoneNumber",PhoneNumberEdit.text.toString())
//                intentInformAgreeToUserInfo.putExtra("Name",NameEdit.text.toString())
//                intentInformAgreeToUserInfo.putExtra("ID",IDEdit.text.toString())
//                intentInformAgreeToUserInfo.putExtra("Password",PWEdit.text.toString())
                startActivity(intentInformAgreeToUserInfo)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }



    }
    private fun isAllChecked() : Boolean{
        return allCheckBox.isChecked
    }
    private fun onCheckChanged(compoundButton: CompoundButton) {
        when(compoundButton.id) {
            R.id.allCheckBox -> {
                if (allCheckBox.isChecked) {
                    firstCheckBox.isChecked = true
                    secondCheckBox.isChecked = true
                    thirdCheckBox.isChecked = true
                    fourthCheckBox.isChecked = true
                }else {
                    firstCheckBox.isChecked = false
                    secondCheckBox.isChecked = false
                    thirdCheckBox.isChecked = false
                    fourthCheckBox.isChecked = false
                }
            }
            else -> {
                allCheckBox.isChecked = (
                        firstCheckBox.isChecked
                                && secondCheckBox.isChecked
                                && thirdCheckBox.isChecked
                                && fourthCheckBox.isChecked)
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_down_enter, R.anim.slide_down_exit)
    }


}