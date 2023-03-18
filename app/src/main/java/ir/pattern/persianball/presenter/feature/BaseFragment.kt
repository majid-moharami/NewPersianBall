package ir.pattern.persianball.presenter.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.databinding.FragmentBaseBinding

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    private var _baseBinding: FragmentBaseBinding? = null
    val baseBinding get() = _baseBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun getChildView(inflater: LayoutInflater, container: ViewGroup?): View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _baseBinding = FragmentBaseBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        baseBinding.content.addView(getChildView(layoutInflater, container))
        return baseBinding.root
    }

    fun showLoading(show: Boolean){
        if (show){
            baseBinding.frameLayout.visibility = View.VISIBLE
            baseBinding.loading.isIndeterminate = true
            baseBinding.loading.visibility = View.VISIBLE
        }else{
            baseBinding.frameLayout.visibility = View.GONE
            baseBinding.loading.isIndeterminate = false
            baseBinding.loading.visibility = View.GONE
        }
    }

    fun showTryAgainView(show: Boolean){
        baseBinding.layoutTryAgain.isVisible = show
    }

    fun showEmptyLayout(show: Boolean) {
        baseBinding.emptyLayout.isVisible = show
    }

    companion object {

    }
}