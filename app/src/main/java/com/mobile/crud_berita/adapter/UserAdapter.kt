package com.mobile.crud_berita.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.crud_berita.DetailUser
import com.mobile.crud_berita.R
import com.mobile.crud_berita.model.ModelUser
import com.mobile.crud_berita.model.UserResponse

class UserAdapter (
    private val onClick: (ModelUser) -> Unit
) : ListAdapter<ModelUser, UserAdapter.UserViewHolder>(UserCallBack) {

    class UserViewHolder(itemView: View, val onClick: (ModelUser) -> Unit) :

        RecyclerView.ViewHolder(itemView) {
        private val username: TextView = itemView.findViewById(R.id.username)
        private val fullname: TextView = itemView.findViewById(R.id.fullname)
        private val email: TextView = itemView.findViewById(R.id.email)
        private val tglDaftar: TextView = itemView.findViewById(R.id.tgl_daftar)
        val cardUser: CardView = itemView.findViewById(R.id.cardUSer)


        //cek produk yang saaat ini

        private var currentProduct: ModelUser? = null

        init {
            itemView.setOnClickListener() {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(user: ModelUser) {
            currentProduct = user
            //set ke widget
            username.text= user.username
            fullname.text = user.fullname
            email.text = user.email
            tglDaftar.text = user.tgl_daftar


//            //untum menampilkan gambar
//            Glide.with(itemView)
//                .load("http://192.168.211.66/beritadb/gambar_berita/" + berita.gambar_berita)
//                .centerCrop()
//                .into(imgBerita)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_user, parent, false
        )
        return UserViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        //detail

        holder.cardUser.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailUser::class.java)
            intent.putExtra("username", user.username)
            intent.putExtra("fullname", user.fullname)
            intent.putExtra("email", user.email)
            intent.putExtra("tgl_daftar", user.tgl_daftar)

            holder.itemView.context.startActivity(intent)
        }
    }
}
object UserCallBack : DiffUtil.ItemCallback<ModelUser>(){
    override fun areItemsTheSame(oldItem: ModelUser, newItem: ModelUser): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ModelUser, newItem: ModelUser): Boolean {
        return oldItem.id == newItem.id
    }

}
