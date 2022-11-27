package com.team7.joongonawa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signin.*

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //회원가입 버튼 클릭 시 회원가입 페이지로 이동
        btnGoToSignUpButton.setOnClickListener {
            var intentSignInToEmailSignUp = Intent(this, SignUpInformAgree::class.java)
            startActivity(intentSignInToEmailSignUp)
            overridePendingTransition(R.anim.slide_up_enter, R.anim.slide_up_exit)
        }

//        logInButton.setOnClickListener {
//
//            if(로그인 정보가 맞으면){
//                var intentSignInToMain = Intent(this, 상품페이지)
//                startActivity(intentSignInToMain)
//                putextra로 회원정보 넘기기
//            }
//            else{
//                토스트나 알림창으로 로그인 정보가 잘못됐다고 알려주기
//            }
//
//        }


    }
    private var backPressedTime : Long = 0
    override fun onBackPressed() {
        Log.d("TAG", "뒤로가기")

        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 1000) {
            finish()
            return
        }

        // 처음 클릭 메시지
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }

}