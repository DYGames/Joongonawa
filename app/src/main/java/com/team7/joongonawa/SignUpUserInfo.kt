package com.team7.joongonawa

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_signup_userinfo.*

class SignUpUserInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_userinfo)


        btnBackToInformAgree.setOnClickListener{

        }


        btnGoToFinish.setOnClickListener {
            if(!validateEmail()){
                return@setOnClickListener //이메일이 올바르지 않으므로 다시 이전단계로 돌아간다.
            }
            else if(!validateID()){
                return@setOnClickListener
            }
            else if(!validatePW()){
                return@setOnClickListener
            }
            else if(!isPWchecked()){
                return@setOnClickListener
            }
            else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("회원가입")
                    .setMessage("회원가입 하시겠습니까?")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, id ->
                            var intentUserInfoToFinish = Intent(this, SignUpFinish::class.java )
//                intentUserInfoToFinish.putExtra("E-mail", EmailEdit.text.toString())
//                intentUserInfoToFinish.putExtra("PhoneNumber",PhoneNumberEdit.text.toString())
//                intentUserInfoToFinish.putExtra("Name",NameEdit.text.toString())
//                intentUserInfoToFinish.putExtra("ID",IDEdit.text.toString())
//                intentUserInfoToFinish.putExtra("Password",PWEdit.text.toString())
                            startActivity(intentUserInfoToFinish)
                            finish()
                            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
                        })
                    .setNegativeButton("취소",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                // 다이얼로그를 띄워주기
                builder.show()
            }
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
    }


    private fun validateEmail(): Boolean {
        val value: String = EmailEdit.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+"

        return if (value.isEmpty()) {
            EmailEdit.error = "이메일을 입력해주세요."
            false
        } else if (!value.matches(emailPattern.toRegex())) {
            EmailEdit.error = "이메일 형식이 잘 못 되었습니다."
            false
        } else {
            EmailEdit.error = null
            true
        }
    }
    private fun validateID(): Boolean {
        val value: String = IDEdit.text.toString()
        val IDPattern = "[a-z0-9]+[a-z0-9]"

        return if (value.isEmpty()) {
            IDEdit.error = "ID를 입력해주세요."
            false
        } else if (!value.matches(IDPattern.toRegex())) {
            IDEdit.error = "ID 형식이 잘 못 되었습니다."
            false
        } else if(value.length<5 || value.length>16){
            IDEdit.error = "ID는 5자 이상 16자 이하여야 힙니다."
            false
        } else {
            IDEdit.error = null
            true
        }
    }
    private fun validatePW(): Boolean {
        val value: String = PWEdit.text.toString()
        val PWPattern = "[a-z0-9!@#\\\$%^&*]+[a-z0-9!@#\\\$%^&*]"

        return if (value.isEmpty()) {
            PWEdit.error = "비밀번호를 입력해주세요."
            false
        } else if (!value.matches(PWPattern.toRegex())) {
            PWEdit.error = "비밀번호 형식이 잘 못 되었습니다."
            false
        } else if(value.length<8 || value.length>20){
            PWEdit.error = "비밀번호는 8자 이상 20자 이하여야 힙니다."
            false
        } else {
            PWEdit.error = null
            true
        }
    }
    private fun isPWchecked(): Boolean{
        val PW:String = PWEdit.text.toString()
        val PWCheck:String = PWCheckEdit.text.toString()

        return if(PW.equals(PWCheck)){
             true
        }
        else{
            PWCheckEdit.error = "비밀번호가 같지 않습니다."
            false
        }
    }


}