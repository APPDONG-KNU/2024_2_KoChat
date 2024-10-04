package org.javaapp.chatting

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.databinding.ActivityMainBinding
import org.javaapp.chatting.userlist.User
import org.javaapp.chatting.userlist.UserListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding // view binding
    private lateinit var auth : FirebaseAuth // Firebase auth
    private var currentUser : FirebaseUser? = null // Firebase currentUser

    private val userListFragment = UserListFragment() // 사용자 리스트 프래그먼트

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

        // 바텀 네비게이션뷰 리스너 설정
        binding.bottomNavigation.setOnItemSelectedListener {menuItem ->
            when(menuItem.itemId) {
                R.id.user_list -> {
                    replaceFragment(userListFragment) // 프래그먼트 교체
                    return@setOnItemSelectedListener true
                }
                R.id.chat_list -> {

                    return@setOnItemSelectedListener true
                }
                R.id.mypage -> {

                    return@setOnItemSelectedListener true
                }
                else ->  {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    // 프래그먼트 교체
    private fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }




    /////////////////////////////// 액션바 메뉴 /////////////////////////////////////
    // 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 메뉴 리소스 파일을 인플레이트하여 액션바에 메뉴 추가
        menuInflater.inflate(R.menu.menu_main, menu);
        
        return true
    }

    // 메뉴 선택 이벤트 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 사용자가 선택한 메뉴의 ID 확인
        return when(item.itemId) {
            R.id.log_out -> { // 로그아웃 메뉴를 선택했을 경우
                // 로그아웃
                auth.signOut()

                // 로그인 액티비티로 이동
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)

                // 현재(메인) 액티비티 종료
                finish()

                true
            } else -> {
                false
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////
}