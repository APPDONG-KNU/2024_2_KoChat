package org.javaapp.chatting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //---------------------------

        auth = Firebase.auth // Firebase auth

        // 로그인 버튼 클릭 리스너
        binding.signInBtn.setOnClickListener {
            // 입력된 이메일/비밀번호 정보
            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()

            // 모두 입력했는지 / 올바른 형식인지 확인 TODO 정규식 활용 및 예외 처리
            if (email.isNullOrBlank() || password.isNullOrBlank()) {
                Toast.makeText(this, "잘못된 이메일/비밀번호 형식", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 괜찮다면 로그인
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { // 로그인 성공
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    
                    // 메인 액티비티로 이동
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    // 로그인 액티비티 종료
                    finish()
                } else { // 로그인 실패
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

        }

        // 회원가입 버튼 클릭 리스너
        binding.signUpBtn.setOnClickListener {
            // 회원가입 액티비티로 이동
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

            // 로그인 액티비티 종료
            finish()
        }
    }
}