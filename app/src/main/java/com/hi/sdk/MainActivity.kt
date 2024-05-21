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
import com.hi.base.data.HiRoleData
import com.hi.base.data.HiUser
import com.hi.base.plugin.pay.IPayCallBack
import com.hi.base.plugin.pay.PayParams
import com.hi.base.pub.HiGameSDK
import com.hi.base.pub.SDKManager
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
       // HiGameSDK.getInstance().onCreate(this)
        bindings!!.button.setOnClickListener { GooglePay("123456789") }
        bindings!!.button2.setOnClickListener { showBannerAd()  }
        bindings!!.button3.setOnClickListener { IntervalAd()   }
        bindings!!.bthLoginGoogle.setOnClickListener { GoogleLogin() }
        bindings!!.video.setOnClickListener { showReward() }

    }

    override fun onStart() {
        super.onStart()
        HiGameSDK.getInstance().onCreate(this)
    }

    private fun showReward() {
     //   TODO("Not yet implemented")
        // 创建事件数据 HashMap
        val eventData = hashMapOf<String?, Any?>()
        eventData["key1"] = "value1"
        eventData["key2"] = 123

        // 调用 onCustomEvent 方法
        onCustomEvent("custom_event", eventData)
        HiGameSDK.getInstance().showRewardVideo(this)

    }

    private fun showBannerAd() {
      //  TODO("Not yet implemented")
        HiGameSDK.getInstance().showBanner(this)
    }


    private fun GoogleLogin() {
      //  TODO("Not yet implemented")
        HiGameSDK.getInstance().Login(this)
    }


    private fun IntervalAd() {
       // TODO("Not yet implemented")
        HiGameSDK.getInstance().showInterstitial(this)
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

    // 自定义上报
    fun onCustomEvent(eventName: String?, eventData: HashMap<String?, Any?>?) {
        // 直接调用 HiGameSDK 的 onCustomEvent 方法，并传入方法参数中的 eventName 和 eventData
        HiGameSDK.getInstance().onCustomEvent(eventName, eventData)
    }
    //完成新手教程的时候 执行
    fun onCompleteTutorial(tutorialID: Int, content: String?) {
        HiGameSDK.getInstance().onCompleteTutorial(tutorialID, content)
    }
    //升级
    fun onLevelup(role: HiRoleData?) {
        HiGameSDK.getInstance().onLevelup(role)
    }
    //自定义事件， 进入游戏成功
    fun onEnterGame(role: HiRoleData?) {
        HiGameSDK.getInstance().onEnterGame(role)
    }
    // 自定义事件， 创建角色成功
    fun onCreateRole(role: HiRoleData?) {
        HiGameSDK.getInstance().onCreateRole(role)
    }
    /**
     * 购买成功的时候，调用
     * af_purchase
     * price: 分为单位
     */
    fun onPurchase(order: HiOrder?) {
        HiGameSDK.getInstance().onPurchase(order)
    }
    /**
     * 自定义事件， 开始购买（SDK下单成功）
     * price：分为单位
     */
    fun onPurchaseBegin(order: HiOrder?) {
        HiGameSDK.getInstance().onPurchaseBegin(order)
    }

    /**
     * 注册成功的时候 上报
     * af_complete_registration
     */
    fun onCompleteRegistration(regType: Int) {
        HiGameSDK.getInstance().onCompleteRegistration(regType)
    }
    /**
     * 登陆成功的时候 上报
     * af_login
     */
    fun onLogin() {
        HiGameSDK.getInstance().onLogin()
    }

    /**
     * 自定义事件： SDK初始化开始
     */
    fun onInitStart() {
        HiGameSDK.getInstance().onInitStart()
    }

    /**
     * 自定义事件： SDK初始化完成
     */
    fun onInitFinish() {
        HiGameSDK.getInstance().onInitFinish()
    }

    /**
     * 自定义事件： SDK登陆开始
     */
    fun onLoginStart() {
        HiGameSDK.getInstance().onLoginStart()
    }
}

