package com.hi.sdk

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hi.base.plugin.itf.IInitCallback
import com.hi.base.plugin.pay.IPayCallBack
import com.hi.base.plugin.pay.PayParams
import com.hi.base.pub.HiGameSDK
import com.hi.sdk.databinding.ActivityMainBinding

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

        HiGameSDK.getInstance().init(this, object :
            IInitCallback {
           override fun onInitSuccess() {
               Log.d(TAG,"onInitSuccess")
              // TODO("Not yet implemented")
           }

           override fun onInitFail(code: Int, msg: String?) {
             //  TODO("Not yet implemented")
               Log.d(TAG,"onInitFail code:$code msg:$msg")
           }

       })
        HiGameSDK.getInstance().setAdInitSDK();
        bindings!!.button.setOnClickListener { GooglePay("123456789") }
        bindings!!.button2.setOnClickListener { BannerAd()  }
        bindings!!.button3.setOnClickListener { IntervalAd()   }
        bindings!!.bthLoginGoogle.setOnClickListener { GoogleLogin() }

    }


    private fun GoogleLogin() {
      //  TODO("Not yet implemented")
    }

    private fun BannerAd() {
      //  TODO("Not yet implemented")
        HiGameSDK.getInstance().showBanner(this,"ca-app-pub-2382347120869101/4630096608")
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

