package org.javaapp.chatting.chatroomlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.javaapp.chatting.chatList
import org.javaapp.chatting.databinding.FragmentChatRoomListBinding
import org.javaapp.chatting.databinding.ItemChatRoomBinding

class ChatRoomListFragment : Fragment() {
    private lateinit var binding : FragmentChatRoomListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리사이클러뷰
        binding.chatRoomListRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ChatRoomAdapter(chatList); // TODO 실제 데이터 리스트
        }
    }

    // 리사이클러뷰 홀더
    private inner class ChatRoomHolder(private val binding : ItemChatRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat : ChatRoom) {
            // binding.profileImage.~~ TODO 프로필 이미지
            binding.nameText.text = chat.otherUserName // 이름
            binding.lastMessageText.text = chat.lastMessage // 상태메시지
        }
    }

    // 리사이클러뷰 어댑터
    private inner class ChatRoomAdapter(private val chatList : List<ChatRoom>) : RecyclerView.Adapter<ChatRoomHolder>() {

        // 뷰홀더 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomHolder {
            // 홀더에 바인딩 정보를 담아 반환
            val binding = ItemChatRoomBinding.inflate(layoutInflater, parent, false)
            return ChatRoomHolder(binding)
        }

        // 리스트의 원소 개수 정보 받기
        override fun getItemCount(): Int {
            // 리스트 원소의 개수 반환
            return chatList.size
        }

        // 홀더와 뷰 연결(바인딩)
        override fun onBindViewHolder(holder: ChatRoomHolder, position: Int) {
            holder.bind(chatList[position])
        }

    }
}