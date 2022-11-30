package com.team7.joongonawa

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import com.team7.joongonawa.databinding.ActivitySigninBinding
import kotlinx.android.synthetic.main.activity_signup_phonenumber.*

class SignUpPhoneNumber : AppCompatActivity() {

    val SMS_SEND_PERMISSION = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_phonenumber)

//        //자동 하이픈
////        PhoneNumberEdit.addTextChangedListener(PhoneNumberFormattingTextWatcher())
//
//        var permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS);
//        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
//
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.SEND_SMS)){
//                Toast.makeText(applicationContext, "SMS 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
//
//
//            }
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),SMS_SEND_PERMISSION)
//        }
//
//        sendSMSbt.setOnClickListener{
//
//            sendSMS(PhoneNumberEdit.text.toString(), "메세지 전송 테스트")
//        }
//
//
//
//
//    }
//    private fun sendSMS(phoneNumber: String, message : String):Unit{
//        val pi = PendingIntent.getActivity(this, 0, Intent(this, SignUpPhoneNumber::class.java),0);
//        var sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, message, pi, null);
//
//        Toast.makeText(baseContext, "메세지가 전송되었습니다.", Toast.LENGTH_SHORT).show();
   }

}