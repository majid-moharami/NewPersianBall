package ir.pattern.persianball.presenter.feature.profile

import android.app.Dialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentProfileBinding
import ir.pattern.persianball.presenter.MainActivity
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.feature.profile.personalInformation.PersonalInfoViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val personalInfoViewModel: PersonalInfoViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null
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
        pagingAdapter = ProfileDataAdapter(childFragmentManager, lifecycle).apply {
            uploadImager = {
                openGalleryForImage()
            }
        }.also {
            binding.recyclerView.adapter = it
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.collectLatest {
                it?.let { recyclerData ->
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                personalInfoViewModel.userPersonalData.collectLatest {
                    it?.also { dto ->
                        viewModel.updateUserInfo(dto)
                    }
                }
            }
        }
        return binding.root
    }

    class StartGameDialogFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return super.onCreateDialog(savedInstanceState)
        }
    }

    private fun openGalleryForImage() {
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
            .start(MainActivity.GALLERY_SELECT_IMAGE_REQUEST_CODE)

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

    fun onImageResult(file: MultipartBody.Part) {
        if (personalInfoViewModel.personalData.username.isNotEmpty()) {
            viewModel.uploadAvatar(personalInfoViewModel.personalData.username, file)
        }
    }
}