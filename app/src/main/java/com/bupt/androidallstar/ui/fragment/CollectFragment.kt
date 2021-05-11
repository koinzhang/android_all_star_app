package com.bupt.androidallstar.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bupt.androidallstar.databinding.FragmentCollectBinding
import com.bupt.androidallstar.ui.adapter.CollectLibraryAdapter
import com.bupt.androidallstar.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class CollectFragment : Fragment() {
    private var _binding: FragmentCollectBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectBinding.inflate(inflater, container, false)
        mainViewModel = requireActivity().getViewModel()
        initView()
        initRegister()
        return binding.root
    }

    private fun initView() {
        binding.rvCollectLibrary.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = CollectLibraryAdapter(mutableListOf())
            (adapter as CollectLibraryAdapter).setOnItemClickListener { adapter, _, position ->
                val intent = Intent()
                intent.action = "android.intent.action.VIEW"
                intent.data =
                    Uri.parse((adapter as CollectLibraryAdapter).data[position].githubUrl);//此处填链接
                startActivity(intent);
            }
        }
    }

    private fun initRegister() {
        mainViewModel.allLibrary.observe(viewLifecycleOwner, {
            Timber.d("it $it")
            (binding.rvCollectLibrary.adapter as CollectLibraryAdapter).apply {
                data = it.toMutableList()
                notifyDataSetChanged()
            }
        })
    }

}