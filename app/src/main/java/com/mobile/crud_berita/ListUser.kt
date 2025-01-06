package com.mobile.crud_berita

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mobile.crud_berita.adapter.UserAdapter
import com.mobile.crud_berita.model.ModelUser
import com.mobile.crud_berita.model.UserResponse
import com.mobile.crud_berita.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUser : AppCompatActivity() {

    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var recycleview  : RecyclerView
    private lateinit var call : Call<UserResponse>
    private lateinit var userAdapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_user)

        swipeRefreshLayout = findViewById(R.id.refresh)
        recycleview = findViewById(R.id.rv_user)

        userAdapter = UserAdapter{modelUser : ModelUser ->(modelUser) }
        recycleview.adapter = userAdapter

        recycleview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,
            false)

        swipeRefreshLayout.setOnRefreshListener {
            getData() //metho untuk proses ambil data
        }

        getData()



    }

    private fun getData() {
        swipeRefreshLayout.isRefreshing = true
        call = ApiClient.retrofit.getAllUser()
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                swipeRefreshLayout.isRefreshing = false
                if(response.isSuccessful){
                    userAdapter.submitList(response.body()?.data)
                    userAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(applicationContext, t.localizedMessage,
                    Toast.LENGTH_SHORT).show()
            }

        })
    }
}