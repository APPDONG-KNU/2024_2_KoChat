package org.javaapp.chatting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth // Firebase auth

        // 회원가입 버튼 리스너
        binding.signUpBtn.setOnClickListener {
            // 작성한 이름, 이메일, 비밀번호 정보
            val name = binding.nameEdit.text.toString()
            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()

            // 모두 작성했는지 / 올바른 형식인지 확인 TODO 정규식 처리
            if (name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank()) {
                Toast.makeText(this, "잘못된 이름/이메일/비밀번호 형식", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 괜찮다면 회원가입
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {task->
                if (task.isSuccessful) { // 회원가입 성공
                    Toast.makeText(this, "회원가입&로그인 성공", Toast.LENGTH_SHORT).show()

                    // 메인 액티비티로 이동
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    // 회원가입 액티비티 종료
                    finish()
                } else { // 회원가입 실패
                    Toast.makeText(this, "회원가입&로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}