package org.javaapp.chatting.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.javaapp.chatting.databinding.ItemUserBinding

class UserListFragment : Fragment() {


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
           val binding = ItemUserBinding.inflate(layoutInflater, parent, false)

            return UserHolder(binding)
        }

        override fun getItemCount(): Int {
            return userList.size
        }

        override fun onBindViewHolder(holder: UserHolder, position: Int) {
            holder.bind(userList[position])
        }

    }
}