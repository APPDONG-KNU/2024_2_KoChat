package org.javaapp.chatting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var auth : FirebaseAuth // 사용자 인증 도구

    private lateinit var currentUser : FirebaseUser // 현재 사용자
    private lateinit var database : DatabaseReference // 데이터베이스


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
                    
                    // TODO 데이터베이스 User 항목에 사용자 정보 추가 및 저장(id, name, statusMessage)
                    currentUser = auth.currentUser!! // 현재 사용자 정보(uid) 가져오기
                    database = Firebase.database(Key.DB_URL).reference // 데이터베이스 연결

                    val userInfo = mutableMapOf<String, Any>( // 데이터베이스에 넣을 현재 사용자 정보(id, 이름, 상태메시지)
                        "id" to currentUser.uid,
                        "name" to name,
                        "statusMessage" to ""
                    )

                    database.child(Key.DB_USER).child(currentUser.uid).updateChildren(userInfo) // 데이터베이스에 사용자 정보 업데이트(추가)



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