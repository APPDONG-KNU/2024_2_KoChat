package org.javaapp.chatting.chat

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
import org.javaapp.chatting.chatMsgList
import org.javaapp.chatting.databinding.FragmentChatBinding
import org.javaapp.chatting.databinding.ItemChatBinding
import org.javaapp.chatting.user.User
import java.util.UUID
import javax.security.auth.callback.Callback

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

        // 채팅 리사이클러뷰 설정
        binding.chatRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ChatAdapter(emptyList())
        }
        
        // 채팅 입력 버튼 리스너 설정
        binding.sendBtn.setOnClickListener { 
            postChat()
        }

        // 채팅 기록 가져오기
        fetchChat()
    }


    // 리사이클러뷰 홀더
    private inner class ChatHolder(private val binding : ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat : Chat) {
            if (chat.userId == currentUser.uid) { // 내가 보낸 채팅일 때
                // 이름
                binding.userNameText.visibility = View.INVISIBLE // 화면에서 가림

                // 메시지 내용
                binding.msgText.apply {  // 메시지 박스 내 오른쪽(END)에 배치
                    text = chat.msg
                    gravity = Gravity.END
                }

                // 메시지 박스
                binding.chatItemLayout.gravity = Gravity.END // 레이아웃의 오른쪽(END)에 배치

            } else { // 다른 사람이 보낸 채팅일 때
                // 이름
                getUserName(database, chat.userId!!, object : UserNameCallback { // 콜백 사용
                    override fun onUserNameRetrieved(userName: String) {
                        binding.userNameText.apply {
                            visibility = View.VISIBLE
                            text = userName
                        }
                    }
                })
                
                // 메시지 내용
                binding.msgText.apply {
                    text = chat.msg
                    gravity = Gravity.START // 메시지 박스 내 왼쪽(START)에 배치
                }
                
                // 메시지 박스
                binding.chatItemLayout.gravity = Gravity.START // 레이아웃의 왼쪽(START)에 배치
            }

        }
    }

    // 리사이클러뷰 어댑터
    private inner class ChatAdapter(private val chatMsgList : List<Chat>) : RecyclerView.Adapter<ChatHolder>() {

        // 뷰홀더 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
            // 홀더에 바인딩 정보를 담아 반환
            val binding = ItemChatBinding.inflate(layoutInflater, parent, false)
            return ChatHolder(binding)
        }

        // 리스트의 원소 개수 정보 받기
        override fun getItemCount(): Int {
            // 리스트 원소의 개수 반환
            return chatMsgList.size
        }

        // 홀더와 뷰 연결(바인딩)
        override fun onBindViewHolder(holder: ChatHolder, position: Int) {
            holder.bind(chatMsgList[position])
        }

    }

    //////////////////////////////////// 사용자 id 정보로부터 사용자 이름 가져오기 /////////////////////////////////////
    interface UserNameCallback {
        fun onUserNameRetrieved(userName : String)
    }

    fun getUserName(database : DatabaseReference, userId : String, callback : UserNameCallback) {
        database.child(Key.DB_USER).child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(User::class.java)
                val userName = userInfo?.name ?: "알 수 없음"

                callback.onUserNameRetrieved(userName)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 채팅 기록 가져오기
    private fun fetchChat() {
        database.child(Key.DB_CHAT).orderByChild("timeStamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatLog = mutableListOf<Chat>()

                snapshot.children.forEach {
                    val chat = it.getValue(Chat::class.java)
                    chat ?: return

                    chatLog.add(chat)
                }

                binding.chatRecyclerview.adapter = ChatAdapter(chatLog)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    // 채팅 보내기
    private fun postChat() {
        val id = UUID.randomUUID().toString() // 현재 시간 기준으로 메시지 고유 아이디값 생성
        val timeStamp = System.currentTimeMillis().toString() // 시간순 정렬을 위한 타임스탬프
        val userId = currentUser.uid // 메시지 작성자 확인 및 이름을 가져오기 위한 아이디
        val msg = formatText(binding.msgEdit.text.toString()) // 메시지 내용

        val chat = mutableMapOf<String, Any>(
            "id" to id,
            "timeStamp" to timeStamp,
            "userId" to userId,
            "msg" to msg
        )

        database.child(Key.DB_CHAT).child(id).setValue(chat)

        // EditText 내용 비우기
        binding.msgEdit.text.clear()

        // 키보드 내리기
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            binding.msgEdit.windowToken,
            0
        )
    }

    // 메시지 내용 포맷팅
    private fun formatText(text : String) : String {
        text.trim()

        while (text.endsWith("\n")) {
            text.dropLast(1)
        }

        return text
    }

}