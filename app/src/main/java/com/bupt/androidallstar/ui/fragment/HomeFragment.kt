package com.bupt.androidallstar.ui.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bupt.androidallstar.R
import com.bupt.androidallstar.databinding.FragmentHomeBinding
import com.bupt.androidallstar.ui.adapter.AndroidLibraryAdapter
import com.bupt.androidallstar.viewmodel.HomeViewModel
import com.permissionx.guolindev.PermissionX
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = requireActivity().getViewModel()
        initPermission()
        initView()
        initRegister()

        return binding.root
    }

    private fun initView() {
        binding.rvAndroidLibrary.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = AndroidLibraryAdapter(mutableListOf())
            (adapter as AndroidLibraryAdapter).setOnItemClickListener { adapter, _, position ->
                val intent = Intent()
                intent.action = "android.intent.action.VIEW"
                intent.data =
                    Uri.parse((adapter as AndroidLibraryAdapter).data[position].githubUrl);//此处填链接
                startActivity(intent);
            }
        }
        //设置共享元素过渡名称
        ViewCompat.setTransitionName(binding.llSearchBar, "search_enter")
        ViewCompat.setTransitionName(binding.imgSearch, "search_enter_img")
        ViewCompat.setTransitionName(binding.txtSearch, "search_enter_txt")

        val extras = FragmentNavigatorExtras(
            binding.llSearchBar to "search_start",
            binding.imgSearch to "search_start_img",
            binding.txtSearch to "search_start_txt"
        )

        binding.llSearchBar.setOnClickListener {
            it.findNavController()
                .navigate(
                    R.id.action_navigation_home_to_searchFragment,
                    null,
                    //切换时共享元素的动画效果，可选
                    null,
                    extras
                )
        }
    }

    private fun initRegister() {
        //ViewModel中的LiveData在视图层中注册监听后，在ViewModel中的数据改变时可以持续收到数据
        homeViewModel.libraryRecommendData.observe(viewLifecycleOwner, {
            Timber.d("t $it")
            (binding.rvAndroidLibrary.adapter as AndroidLibraryAdapter).apply {
                data = it
                notifyDataSetChanged()
            }
        })
    }

    private fun initPermission() {
        PermissionX.init(this)
            .permissions(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                } else {
                    Toast.makeText(
                        requireContext(),
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}