package com.rehman.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rehman.room.databinding.ActivityMainBinding
import com.rehman.room.databinding.ItemRowBinding
import com.rehman.room.models.UserModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUsers()

        lifecycleScope.launch {
            viewModel.fetchUser.collect {
                initRecycler(it)
            }
        }

        listeners()


    }

    private fun initRecycler(list: ArrayList<UserModel>) {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserAdapter(list)
        }
    }

    private fun listeners() {
        binding.save.setOnClickListener {
            if (binding.name.text.isEmpty() || binding.age.text.isEmpty()) {
                Toast.makeText(this, "Field's can't be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val userData = UserModel(
                name = binding.name.text.toString().trim(),
                age = binding.age.text.toString().trim().toInt()
            )
            viewModel.insertUser(userData)

            binding.name.setText("")
            binding.age.setText("")


        }
    }

    inner class UserAdapter(private val list: ArrayList<UserModel>) :
        RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemRowBinding.inflate(layoutInflater, parent, false)
            return UserViewHolder(itemBinding)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val item = list[position]
            holder.bind(item)
        }

        inner class UserViewHolder(private val binding: ItemRowBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: UserModel) {
                // Bind your data here using the binding instance, e.g.,
                binding.name1.text = item.name
                binding.age1.text = item.age.toString()

                binding.root.setOnClickListener {
                    viewModel.deleteUser(item.id)
                    viewModel.getUsers()
                }
            }
        }

    }

}