package org.javaapp.chatting.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.SignInActivity
import org.javaapp.chatting.databinding.FragmentChatListBinding
import org.javaapp.chatting.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private lateinit var binding : FragmentMyPageBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
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

        // TODO 현재 사용자 이름과 상태 메시지 보여주기


        // 저장하기 버튼 클릭 리스너 설정
        binding.saveBtn.setOnClickListener {
            val name = binding.nameEdit.text.toString()
            val statusMessage = binding.statusMessageEdit.text.toString()
            
            // 저장
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
}