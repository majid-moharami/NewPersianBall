package ir.pattern.persianball.presenter.feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentBaseBinding
import ir.pattern.persianball.databinding.FragmentLoginBinding


open class BaseFragment : Fragment() {

    lateinit var baseBinding: FragmentBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        baseBinding = FragmentBaseBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return baseBinding.root
    }

    companion object {

    }
}