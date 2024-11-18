package org.javaapp.chatting.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.chatMsgList
import org.javaapp.chatting.databinding.FragmentChatBinding
import org.javaapp.chatting.databinding.ItemChatMsgBinding

class ChatFragment : Fragment() {
    private lateinit var binding : FragmentChatBinding
    private lateinit var currentUser : FirebaseUser
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentUser = Firebase.auth.currentUser!! // 현재 사용자 정보
        database = Firebase.database.reference // 데이터베이스 레퍼런스
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chatMsgRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ChatMsgAdapter(chatMsgList)
        }
    }


    // 리사이클러뷰 홀더
    private inner class ChatMsgHolder(private val binding : ItemChatMsgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMsg : Chat) {
            binding.userNameText.text = "밖에서 가져오기" // TODO
            binding.msgText.text = chatMsg.msg
        }
    }

    // 리사이클러뷰 어댑터
    private inner class ChatMsgAdapter(private val chatMsgList : List<Chat>) : RecyclerView.Adapter<ChatMsgHolder>() {

        // 뷰홀더 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMsgHolder {
            // 홀더에 바인딩 정보를 담아 반환
            val binding = ItemChatMsgBinding.inflate(layoutInflater, parent, false)
            return ChatMsgHolder(binding)
        }

        // 리스트의 원소 개수 정보 받기
        override fun getItemCount(): Int {
            // 리스트 원소의 개수 반환
            return chatMsgList.size
        }

        // 홀더와 뷰 연결(바인딩)
        override fun onBindViewHolder(holder: ChatMsgHolder, position: Int) {
            holder.bind(chatMsgList[position])
        }

    }
}