package ir.pattern.persianball.presenter.feature.profile

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.databinding.FragmentProfileBinding
import ir.pattern.persianball.manager.AccountManager
import ir.pattern.persianball.presenter.MainActivity
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.academy.AcademyFragmentDirections
import ir.pattern.persianball.presenter.feature.login.LoginActivity
import ir.pattern.persianball.presenter.feature.profile.personalInformation.PersonalInfoViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val personalInfoViewModel: PersonalInfoViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null

    @Inject
    lateinit var accountManager: AccountManager

    private val isLogin = MutableSharedFlow<Boolean>()
    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            isLogin.emit(accountManager.isLogin)
            viewModel.profileRepository._isLogin.emit(accountManager.isLogin)
        }
        viewModel.setUpData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )
        showLoading(true)
        viewLifecycleOwner.lifecycleScope.launch {
            isLogin.collectLatest {
                when (it) {
                    true -> {
                        binding.notLoginLayout.visibility = View.GONE
                        viewModel.setUpData()
                        binding.recyclerView.visibility = View.VISIBLE
                    }
                    false -> {
                        showLoading(false)
                        binding.notLoginLayout.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.collect {
                it?.let { recyclerData ->
                    pagingAdapter = ProfileDataAdapter(childFragmentManager, lifecycle).apply {
                        uploadImager = {
                            openGalleryForImage(MainActivity.GALLERY_SELECT_IMAGE_REQUEST_CODE)
                        }
                        uploadBackground = {
                            openGalleryForImage(MainActivity.GALLERY_SELECT_BACK_IMAGE_REQUEST_CODE)
                        }
                        onUsernameClickListener =
                            BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                                val dialog = ChangeUserNameDialog.newInstance()
                                dialog.show(childFragmentManager, "edit_info_dialog")
                            }
                    }.also {
                        binding.recyclerView.adapter = it
                    }
                    binding.recyclerView.layoutManager =
                        LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    showLoading(false)
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                personalInfoViewModel.userPersonalData.collectLatest {
                    it?.also { dto ->
                        viewModel.updateUserInfo(dto)
                        showLoading(false)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLogin.collect {
                it?.also {
                    when (it) {
                        true -> {
                            binding.notLoginLayout.visibility = View.GONE
                            viewModel.setUpData()
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        false -> {
                            showLoading(false)
                            binding.notLoginLayout.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                    }
                }
            }
        }
        binding.loginBtn.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.putExtra("IS_LOGIN", true)
            startActivity(intent)
        }
        return binding.root
    }

    class StartGameDialogFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return super.onCreateDialog(savedInstanceState)
        }
    }

    private fun openGalleryForImage(tag: Int) {
        ImagePicker.with(requireActivity())
            // Crop Image(User can choose Aspect Ratio)
            .crop()
            // User can only select image from Gallery
            .galleryOnly()

            .galleryMimeTypes( // no gif images at all
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            // Image resolution will be less than 1080 x 1920
            .maxResultSize(1080, 1920)
            .start(tag)
    }

    private fun openCameraForImage() {
        ImagePicker.with(requireActivity())
            // Crop Image(User can choose Aspect Ratio)
            .crop()
            // User can only capture image from Camera
            .cameraOnly()
            // Image size will be less than 1024 KB
            .compress(1024)
            // Image resolution will be less than 1080 x 1920
            .maxResultSize(1080, 1920)
            //  Path: /storage/sdcard0/Android/data/package/files/DCIM
            .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM)!!)

            .start(MainActivity.CAPTURE_IMAGE_REQUEST_CODE)
    }

    fun onImageResult(file: MultipartBody.Part, isAvatar: Boolean) {
        if (personalInfoViewModel.personalData.username.isNotEmpty()) {
            viewModel.uploadAvatar(personalInfoViewModel.personalData.username, file, isAvatar)
        }
    }

    fun showLoading(show: Boolean) {
        if (show) {
            binding.frameLayout.visibility = View.VISIBLE
            binding.loading.isIndeterminate = true
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.frameLayout.visibility = View.GONE
            binding.loading.isIndeterminate = false
            binding.loading.visibility = View.GONE
        }
    }
}