package com.example.palindrome

import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.palindrome.databinding.ActivityThirdBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var adapter: UserAdapter
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSwipeRefresh()
        fetchUsers()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(mutableListOf()) { user ->
            // Send selected user data back to SecondActivity
            val intent = Intent()
            val fullName = "${user.first_name} ${user.last_name}"
            intent.putExtra("SELECTED_USER", fullName) // Pass selected user's name
            setResult(RESULT_OK, intent) // Set the result to send data back
            finish() // Close ThirdActivity and return to SecondActivity
        }
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            adapter.updateData(emptyList()) // Clear the adapter
            fetchUsers()
        }

        binding.userRecyclerView.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(binding.userRecyclerView.layoutManager as LinearLayoutManager) {
            override fun onLoadMore() {
                currentPage++
                fetchUsers()
            }
        })
    }

    private fun fetchUsers() {
        binding.swipeRefreshLayout.isRefreshing = true
        ApiClient.apiService.getUsers(currentPage, 10).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.data ?: emptyList()
                    adapter.updateData(users)
                } else {
                    Toast.makeText(this@ThirdActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@ThirdActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }


}
