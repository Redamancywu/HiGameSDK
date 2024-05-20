package com.hi.sdk

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hi.base.HiGameListener
import com.hi.base.data.HiOrder
import com.hi.base.data.HiProduct
import com.hi.base.data.HiUser
import com.hi.base.plugin.pay.IPayCallBack
import com.hi.base.plugin.pay.PayParams
import com.hi.base.pub.HiGameSDK
import com.hi.sdkdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var TAG="MainActivity"
    private var bindings: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings= ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(bindings?.root)
        bindings?.root?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
        HiGameSDK.getInstance().onCreate(this)
        HiGameSDK.getInstance().init(this, object :
            HiGameListener {
            override fun onInitFailed(code: Int, msg: String?) {
                TODO("Not yet implemented")
            }

            override fun onInitSuccess() {
               Log.d(TAG,"onInitSuccess")
              // TODO("Not yet implemented")
           }

            override fun onLogout() {
                TODO("Not yet implemented")
            }

            override fun onLoginSuccess(user: HiUser?) {
                TODO("Not yet implemented")
                Log.d(TAG,"onLoginSuccess :"+user.toString())
            }

            override fun onLoginFailed(code: Int, msg: String?) {
                Log.d(TAG,"onLoginFailed :"+msg)
                TODO("Not yet implemented")
            }

            override fun onUpgradeSuccess(user: HiUser?) {
                TODO("Not yet implemented")

            }

            override fun onProductsResult(code: Int, products: MutableList<HiProduct>?) {
                TODO("Not yet implemented")
            }

            override fun onPaySuccess(order: HiOrder?) {
                TODO("Not yet implemented")
            }

            override fun onPayFailed(code: Int, msg: String?) {
                TODO("Not yet implemented")
            }

            override fun onExitSuccess() {
                TODO("Not yet implemented")
            }


        })
        HiGameSDK.getInstance().setAdInitSDK();
        bindings!!.button.setOnClickListener { GooglePay("123456789") }
        bindings!!.button2.setOnClickListener { showBannerAd()  }
        bindings!!.button3.setOnClickListener { IntervalAd()   }
        bindings!!.bthLoginGoogle.setOnClickListener { GoogleLogin() }

    }

    private fun showBannerAd() {
      //  TODO("Not yet implemented")
        HiGameSDK.getInstance().showBanner(this,"ca-app-pub-2382347120869101/4630096608")
    }


    private fun GoogleLogin() {
      //  TODO("Not yet implemented")
        HiGameSDK.getInstance().Login(this)
    }


    private fun IntervalAd() {
       // TODO("Not yet implemented")
        HiGameSDK.getInstance().showInterstitial(this,"ca-app-pub-2382347120869101/4630096608")
    }

    fun GooglePay(id:String){
        var payParams= PayParams()
        payParams.orderId=id
        HiGameSDK.getInstance().Pay(this,payParams,object :IPayCallBack{
            override fun onPaySuccess(orderId: String?) {
                TODO("Not yet implemented")
            }

            override fun onPayFailure(orderId: String?, errorCode: Int, errorMessage: String?) {
                TODO("Not yet implemented")
            }

            override fun onPayCanceled(orderId: String?) {
                TODO("Not yet implemented")
            }

        })
    }


}

