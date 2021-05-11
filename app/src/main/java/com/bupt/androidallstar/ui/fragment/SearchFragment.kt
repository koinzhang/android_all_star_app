package com.bupt.androidallstar.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import com.bupt.androidallstar.R
import com.bupt.androidallstar.databinding.FragmentSearchBinding
import com.bupt.androidallstar.ui.adapter.AndroidLibraryAdapter
import com.bupt.androidallstar.ui.adapter.LabelAdapter
import com.bupt.androidallstar.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.sharedElementsUseOverlay = false
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.slide)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.slide)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        mainViewModel = requireActivity().getViewModel()
        initView()
        initRegister()
        return binding.root
    }

    private fun initView() {

        ViewCompat.setTransitionName(binding.llSearchBar, "search_start")
        ViewCompat.setTransitionName(binding.imgSearch, "search_start_img")
        ViewCompat.setTransitionName(binding.edtSearch, "search_start_txt")
        ViewCompat.setTransitionName(binding.txtCancel, "search_start_cancel_txt")

        mainViewModel.getAllLabel()

        binding.rvAllLabel.apply {
            layoutManager = StaggeredGridLayoutManager(6, RecyclerView.VERTICAL)
            adapter = LabelAdapter(mutableListOf())
            (adapter as LabelAdapter).setOnItemClickListener { adapter, _, position ->
                Timber.d("click $position")
                mainViewModel.searchLabelLibrary((adapter as LabelAdapter).data[position])
                binding.edtSearch.text = (adapter as LabelAdapter).data[position]
            }
        }

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

        binding.imgDelete.setOnClickListener {
            it.visibility = View.INVISIBLE
            binding.apply {
                edtSearch.text = ""
                rvAndroidLibrary.visibility = View.INVISIBLE
                rvAllLabel.visibility = View.VISIBLE
            }
        }
        binding.txtCancel.setOnClickListener {
            binding.rvAllLabel.visibility = View.INVISIBLE
            binding.rvAndroidLibrary.visibility = View.INVISIBLE
            it.findNavController().navigateUp()
        }
    }

    private fun initRegister() {
        //ViewModel中的LiveData在视图层中注册监听后，在ViewModel中的数据改变时可以持续收到数据
        mainViewModel.allLabelData.observe(viewLifecycleOwner, {
            Timber.d("allLabelData $it")
            //初始化标签
            (binding.rvAllLabel.adapter as LabelAdapter).apply {
                data = it
                notifyDataSetChanged()
            }
        })

        mainViewModel.librarySearchLabelData.observe(viewLifecycleOwner, {
            Timber.d("librarySearchLabelData $it")
            binding.apply {
                rvAllLabel.visibility = View.INVISIBLE
                rvAndroidLibrary.visibility = View.VISIBLE
                imgDelete.visibility = View.VISIBLE
            }
            (binding.rvAndroidLibrary.adapter as AndroidLibraryAdapter).apply {
                data = it
                notifyDataSetChanged()
            }
        })
    }
}