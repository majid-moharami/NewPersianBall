//package ir.pattern.persianball.presenter.feature.productDetail.dialog
//
//import android.app.Dialog
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.paging.PagingData
//import androidx.recyclerview.widget.LinearLayoutManager
//import dagger.hilt.android.AndroidEntryPoint
//import ir.pattern.persianball.R
//import ir.pattern.persianball.data.model.RecyclerItem
//import ir.pattern.persianball.data.model.base.RecyclerData
//import ir.pattern.persianball.databinding.CourseChosenDialogBinding
//import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
//import ir.pattern.persianball.presenter.adapter.BaseViewHolder
//import ir.pattern.persianball.presenter.feature.movie.MovieDetailViewModel
//import ir.pattern.persianball.presenter.feature.movie.dialog.DialogChooseDataAdapter
//import ir.pattern.persianball.presenter.feature.productDetail.ProductDetailViewModel
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.launch
//
//
//@AndroidEntryPoint
//class ProductChooseDialog(val title:String, val list: List<RecyclerItem>) :
//    DialogFragment() {
//
//    lateinit var binding: CourseChosenDialogBinding
//    private val dialogViewModel: ProductDetailViewModel by viewModels({ requireParentFragment() })
//    var pagingAdapter: BasePagingAdapter? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = DataBindingUtil.inflate(
//            LayoutInflater.from(requireActivity()),
//            R.layout.course_chosen_dialog,
//            container,
//            false
//        )
//        binding.title.text = title
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.recyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//
//        pagingAdapter = DialogChooseDataAdapter().also {
//            binding.recyclerView.adapter = it
//
//            it.onCoachClickListener =
//                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
//                    dialogViewModel.setSelectedCoach(recyclerData.coachDto)
//                    dismiss()
//                }
//
//            it.onLocationClickListener =
//                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
//                    dialogViewModel.setSelectedLocation(recyclerData.timeAndLocationsDto)
//                    dismiss()
//                }
//
//            it.onGiftClickListener =
//                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
//                    dialogViewModel.setSelectedGift(recyclerData.giftProductDto)
//                    dismiss()
//                }
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            pagingAdapter?.submitData(RecyclerData(flowOf(PagingData.from(list))))
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        val dialog: Dialog? = dialog
//        dialog?.let {
//            it.setCancelable(true)
//            it.window?.also { window ->
//                with(window) {
//                    setLayout(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                    )
//                    setGravity(Gravity.CENTER)
//                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                }
//            }
//        }
//    }
//
//    companion object {
//        fun newInstance(
//            title: String,
//            list: List<RecyclerItem>
//        ): DetailChooseDialogFragment {
//            return DetailChooseDialogFragment(title, list)
//        }
//    }
//}