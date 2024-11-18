package org.javaapp.chatting.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.Key
import org.javaapp.chatting.SignInActivity
import org.javaapp.chatting.databinding.FragmentMyPageBinding
import org.javaapp.chatting.user.User

class MyPageFragment : Fragment() {
    private lateinit var binding : FragmentMyPageBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var currentUser : FirebaseUser
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth // auth
        currentUser = auth.currentUser!! // 현재 사용자 정보
        database = Firebase.database.reference // 데이터베이스 레퍼런스
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    // 뷰에 대한 이벤트 처리 (이벤트 리스너 설정)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 사용자 이름과 상태 메시지 보여주기
        fetchMyInfo()


        // 저장하기 버튼 클릭 리스너 설정
        binding.saveBtn.setOnClickListener {
            val name = binding.nameEdit.text.toString()
            val statusMessage = binding.statusMessageEdit.text.toString()

            if (name.isNullOrBlank()) {
                Toast.makeText(context, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // 내 정보 업데이트 하기
            updateMyInfo()
        }

        // 로그아웃 버튼 클릭 리스너 설정
        binding.signOutBtn.setOnClickListener {
            // 로그아웃
            auth.signOut()

            // 로그인 화면으로 이동
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)

            // 현재 프래그먼트의 호스트 액티비티 종료
            requireActivity().finish()
        }
    }

    private fun fetchMyInfo() {
        database.child(Key.DB_USER).child(currentUser.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val myInfo = snapshot.getValue(User::class.java) // 내 정보 가져오기
                myInfo ?: return

                binding.nameEdit.setText(myInfo.name) // 현재 저장된 내 이름 보여주기
                binding.statusMessageEdit.setText(myInfo.statusMessage) // 현재 저장된 내 상태메시지 보여주기
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun updateMyInfo() {
        val id = currentUser.uid
        val name = binding.nameEdit.text.toString()
        val statusMessage = binding.statusMessageEdit.text.toString()

        val myInfo = mutableMapOf<String, Any>(
            // "id" to id,
            "name" to name,
            "statusMessage" to statusMessage,
        )

        database.child(Key.DB_USER).child(id).updateChildren(myInfo)
    }
}