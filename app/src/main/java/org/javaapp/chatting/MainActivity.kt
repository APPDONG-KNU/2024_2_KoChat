package org.javaapp.chatting

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding // view binding
    private lateinit var auth : FirebaseAuth // Firebase auth
    private var currentUser : FirebaseUser? = null // Firebase currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth // Firebase auth
        currentUser = auth.currentUser // Firebase currentUser

        // 현재 사용자 정보 확인 (현재 로그인이 되어있는지 확인)
        if (currentUser == null) { // 로그인이 되어있지 않으면
            // 로그인 액티비티로 이동
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)

            // 메인 액티비티 종료
            finish()
        }
    }
}