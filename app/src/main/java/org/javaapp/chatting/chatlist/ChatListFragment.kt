package org.javaapp.chatting.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.javaapp.chatting.chatList
import org.javaapp.chatting.databinding.FragmentChatListBinding
import org.javaapp.chatting.databinding.FragmentUserListBinding
import org.javaapp.chatting.databinding.ItemChatBinding
import org.javaapp.chatting.databinding.ItemUserBinding
import org.javaapp.chatting.userlist.User

class ChatListFragment : Fragment() {
    private lateinit var binding : FragmentChatListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리사이클러뷰
        binding.chatListRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ChatAdapter(chatList); // TODO 실제 데이터 리스트
        }
    }

    // 리사이클러뷰 홀더
    private inner class ChatHolder(private val binding : ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat : Chat) {
            // binding.profileImage.~~ TODO 프로필 이미지
            binding.nameText.text = chat.otherUserName // 이름
            binding.lastMessageText.text = chat.lastMessage // 상태메시지
        }
    }

    // 리사이클러뷰 어댑터
    private inner class ChatAdapter(private val chatList : List<Chat>) : RecyclerView.Adapter<ChatHolder>() {

        // 뷰홀더 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
            // 홀더에 바인딩 정보를 담아 반환
            val binding = ItemChatBinding.inflate(layoutInflater, parent, false)
            return ChatHolder(binding)
        }

        // 리스트의 원소 개수 정보 받기
        override fun getItemCount(): Int {
            // 리스트 원소의 개수 반환
            return chatList.size
        }

        // 홀더와 뷰 연결(바인딩)
        override fun onBindViewHolder(holder: ChatHolder, position: Int) {
            holder.bind(chatList[position])
        }

    }
}