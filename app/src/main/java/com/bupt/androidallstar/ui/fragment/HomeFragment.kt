package com.bupt.androidallstar.ui.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bupt.androidallstar.databinding.FragmentHomeBinding
import com.bupt.androidallstar.repository.BmobRepository
import com.bupt.androidallstar.ui.dapter.AndroidLibraryAdapter
import com.bupt.androidallstar.viewmodel.HomeViewModel
import com.bupt.androidallstar.viewmodelfactory.HomeViewModelFactory
import com.permissionx.guolindev.PermissionX
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var bmobRepository: BmobRepository
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewModelFactory: HomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bmobRepository = BmobRepository()
        homeViewModelFactory = HomeViewModelFactory(bmobRepository)
        homeViewModel =
            ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()
        initRegister()
        initPermission()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getAllRecommendLibrary()
    }

    private fun initView() {
        binding.rvAndroidLibrary.apply {
            Timber.d("mAndroidLibraryList111 ")
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
    }

    private fun initRegister() {
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