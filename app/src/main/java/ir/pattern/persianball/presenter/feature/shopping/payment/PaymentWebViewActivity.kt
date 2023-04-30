package ir.pattern.persianball.presenter.feature.shopping.payment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ir.pattern.persianball.databinding.ActivityPaymentWebViewBinding


class PaymentWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentWebViewBinding
    override fun onResume() {
        super.onResume()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra("url")
        binding.webView.apply {
            webViewClient = WebViewClient()
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            with(settings) {
                javaScriptEnabled = true
                allowContentAccess = true
                domStorageEnabled = true
                loadsImagesAutomatically = true
            }
        }
        binding.webView.addJavascriptInterface(this, "Persianball")
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(
                    this@PaymentWebViewActivity,
                    "Error:$description",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        if (url != null) {
            binding.webView.loadUrl(url)
        }
    }

    @JavascriptInterface
    fun paymentResult(isSuccessFul: String) {
        val resultIntent = Intent()
//        when (isSuccessFul) {
//            "true" -> {
//
//        Toast.makeText(this, isSuccessFul.toString(), Toast.LENGTH_LONG).show()
//            }
//            "false" -> {
//                Toast.makeText(this, isSuccessFul.toString(), Toast.LENGTH_LONG).show()
//
//            }
//        }
        resultIntent.putExtra("isPaymentSuccess", isSuccessFul)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}