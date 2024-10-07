package org.javaapp.chatting.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.javaapp.chatting.databinding.FragmentUserListBinding
import org.javaapp.chatting.databinding.ItemUserBinding
import org.javaapp.chatting.userList

class UserListFragment : Fragment() {
    private lateinit var binding : FragmentUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userListRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = UserAdapter(userList); // TODO 실제 데이터 리스트
        }
    }

    // 리사이클러뷰 홀더
    private inner class UserHolder(private val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : User) {
            // binding.profileImage.~~ TODO 프로필 이미지
            binding.nameText.text = user.name // 이름
            binding.statusMessageText.text = user.statusMessage // 상태메시지
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