package com.team7.joongonawa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signin.IDEdit
import kotlinx.android.synthetic.main.activity_signin.PWEdit

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val userViewModel = UserViewModel(UserRepository.instance)

        userViewModel.signinState.observe(this) {
            if(it == 1) {
                //주원이 상품페이지로 넘어가기
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                var intentUserInfoToFinish = Intent(this, ItemListActivity::class.java)
                startActivity(intentUserInfoToFinish)
                finish()
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
            else if(it == 2)
                Toast.makeText(this, "로그인 정보가 맞지 않습니다.", Toast.LENGTH_SHORT).show()
        }

        //회원가입 버튼 클릭 시 회원가입 페이지로 이동
        btnGoToSignUpButton.setOnClickListener {
            var intentSignInToEmailSignUp = Intent(this, SignUpInformAgree::class.java)
            startActivity(intentSignInToEmailSignUp)
            overridePendingTransition(R.anim.slide_up_enter, R.anim.slide_up_exit)
        }

        logInButton.setOnClickListener {
            userViewModel.signinUser(
                UserData(

                    IDEdit.text.toString(),
                    "",
                    PWEdit.text.toString(),
                )
            )
        }


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