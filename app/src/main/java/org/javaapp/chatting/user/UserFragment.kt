package org.javaapp.chatting.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.Key
import org.javaapp.chatting.databinding.FragmentUserBinding
import org.javaapp.chatting.databinding.ItemUserBinding

class UserFragment : Fragment() {
    private lateinit var binding : FragmentUserBinding
    private lateinit var currentUser : FirebaseUser // 현재 사용자
    private lateinit var database: DatabaseReference // 데이터베이스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentUser = Firebase.auth.currentUser!! // 현재 사용자
        database = Firebase.database(Key.DB_URL).reference // 데이터베이스
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userListRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = UserAdapter(emptyList()); // TODO 실제 데이터 리스트
        }

        // 사용자 리스트 불러오기
        database.child(Key.DB_USER).addListenerForSingleValueEvent(object : ValueEventListener {
            // 데이터를 가져오는데 성공했을 경우 실행
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>() // 사용자 정보를 담을 리스트

                snapshot.children.forEach { // 데이터베이스 snapshot의 children을 순회
                    val user = it.getValue(User::class.java) ?: return // 데이터베이스의 User 정보가 User 데이터 클래스에 매핑되지 않을 경우, 리턴

                    if (user.id != currentUser.uid) { // 나(현재 사용자)를 제외한 다른 사용자 정보 리스트에 추가
                        userList.add(user)
                    }
                }

                binding.userListRecyclerview.adapter = UserAdapter(userList) // TODO 어댑터 재설정이 아닌 데이터 추가로 리팩토링
            }

            // 데이터를 가져오는데 실패한 경우 실행
            override fun onCancelled(error: DatabaseError) {
                // TODO 에러 처리
            }
        })
    }

    // 리사이클러뷰 홀더
    private inner class UserHolder(private val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : User) {
            // binding.profileImage.~~ TODO 프로필 이미지
            binding.nameText.text = user.name // 이름
            // 상태메시지
            if (user.statusMessage.isNullOrBlank()) { // 상태메시지가 없을 경우
                binding.statusMessageText.isVisible = false // 화면에서 뷰 숨기기
            } else { // 상태메시지가 있을 경우
                binding.statusMessageText.text = user.statusMessage // 상태메시지 표시
            }
        }
    }

    // 리사이클러뷰 어댑터
    private inner class UserAdapter(private val userList : List<User>) : RecyclerView.Adapter<UserHolder>() {

        // 뷰홀더 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
            // 홀더에 바인딩 정보를 담아 반환
            val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
            return UserHolder(binding)
        }

        // 리스트의 원소 개수 정보 받기
        override fun getItemCount(): Int {
            // 리스트 원소의 개수 반환
            return userList.size
        }

        // 홀더와 뷰 연결(바인딩)
        override fun onBindViewHolder(holder: UserHolder, position: Int) {
            holder.bind(userList[position])
        }

    }
}