package com.hi.sdk

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hi.base.model.HiAdType
import com.hi.base.plugin.ad.HiAdListener
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
        HiGameSDK.getInstance().setAdListener(object :HiAdListener{
            override fun onAdShow(type: String?) {
              //  TODO("Not yet implemented")
            }

            override fun onAdFailed(type: String?, errorMsg: String?) {
               // TODO("Not yet implemented")
            }

            override fun onAdReady(type: String?) {
              //  TODO("Not yet implemented")
            }

            override fun onAdClick(type: String?) {
              //  TODO("Not yet implemented")
            }

            override fun onAdClose(type: String?) {
              //  TODO("Not yet implemented")
            }

            override fun onAdReward(type: String?) {
               // TODO("Not yet implemented")
            }

            override fun onAdRevenue(type: String?) {
               // TODO("Not yet implemented")
            }

        })
        bindings!!.button.setOnClickListener { GooglePay("123456789") }
        bindings!!.button2.setOnClickListener { BannerAd()  }
        bindings!!.button3.setOnClickListener { IntervalAd()   }

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
    fun BannerAd(){
        HiGameSDK.getInstance().show(HiAdType.Banner,"ca-app-pub-2382347120869101/1444890093");
    }
    fun IntervalAd(){
        HiGameSDK.getInstance().show(HiAdType.Inters,"ca-app-pub-2382347120869101/4630096608")
    }

}