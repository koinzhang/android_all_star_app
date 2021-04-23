package com.bupt.androidallstar.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bupt.androidallstar.R
import com.bupt.androidallstar.databinding.FragmentSearchBinding
import com.bupt.androidallstar.ui.dapter.AndroidLibraryAdapter
import com.bupt.androidallstar.utils.Tools
import com.bupt.androidallstar.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        homeViewModel = requireActivity().getViewModel()
        initView()
        initRegister()
        return binding.root
    }

    private fun initView() {

        ViewCompat.setTransitionName(binding.llSearchBar, "search_start")
        ViewCompat.setTransitionName(binding.imgSearch, "search_start_img")
        ViewCompat.setTransitionName(binding.edtSearch, "search_start_txt")

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

        binding.txtCancel.setOnClickListener {
            Tools.hideKeyboard(it)
            it.findNavController().navigateUp()
        }
        // TODO: 4/23/21 显示隐藏软键盘
        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.keyCode == KeyEvent.KEYCODE_SEARCH)) {
                homeViewModel.searchLabelLibrary("${v.text}")
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.edtSearch.requestFocus()
    }

    private fun initRegister() {
        //ViewModel中的LiveData在视图层中注册监听后，在ViewModel中的数据改变时可以持续收到数据
        homeViewModel.librarySearchLabelData.observe(viewLifecycleOwner, {
            Timber.d("t $it")
            (binding.rvAndroidLibrary.adapter as AndroidLibraryAdapter).apply {
                data = it
                notifyDataSetChanged()
            }
        })
    }
}