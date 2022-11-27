package com.team7.joongonawa


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_signup_finish.*



class SignUpFinish : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_finish)

        btnGoToSignIn.setOnClickListener {

            var intentFinishToSignIn = Intent(this, SignIn::class.java )
            startActivity(intentFinishToSignIn)
            overridePendingTransition(R.anim.slide_down_enter, R.anim.slide_down_exit)
            ActivityCompat.finishAffinity(this)

        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intentFinishToSignIn = Intent(this, SignIn::class.java )
        startActivity(intentFinishToSignIn)
        overridePendingTransition(R.anim.slide_down_enter, R.anim.slide_down_exit)
        ActivityCompat.finishAffinity(this)

    }


}