package org.javaapp.chatting.chatmsg

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.javaapp.chatting.chatMsgList
import org.javaapp.chatting.databinding.ActivityChatMsgBinding
import org.javaapp.chatting.databinding.ItemChatMsgBinding

class ChatMsgActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatMsgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatMsgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatMsgRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ChatMsgAdapter(chatMsgList)
        }
    }

    // 리사이클러뷰 홀더
    private inner class ChatMsgHolder(private val binding : ItemChatMsgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMsg : ChatMsg) {
            binding.userNameText.text = "밖에서 가져오기" // TODO
            binding.msgText.text = chatMsg.msg
        }
    }

    // 리사이클러뷰 어댑터
    private inner class ChatMsgAdapter(private val chatMsgList : List<ChatMsg>) : RecyclerView.Adapter<ChatMsgHolder>() {

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