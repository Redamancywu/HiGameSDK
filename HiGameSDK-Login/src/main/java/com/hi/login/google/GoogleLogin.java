package com.hi.login.google;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hi.base.HiGameListener;
import com.hi.base.data.HiUser;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.utils.Constants;


public class GoogleLogin {
    private static GoogleLogin instance;
    private HiGameConfig config;
    private Context mContext;
    private String mClientId;
    private Activity mActivity;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private HiGameListener listener;

    public  static GoogleLogin getInstance() {
        if (instance==null){
            instance=new GoogleLogin();
        }
        return instance;
    }
    public void setListener(HiGameListener listener){
        this.listener=listener;
    }

    //初始化
    public void init(Activity activity, HiGameConfig config){
//        this.mContext=context;
        this.mActivity=activity;
        this.config=config;

        if (config.contains("google_client_id")){
            mClientId=config.getString("google_client_id");
        }
        FirebaseApp.initializeApp(activity);
        // 初始化 Firebase 身份验证
        mAuth = FirebaseAuth.getInstance();

        // 配置 Google 登录选项
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mClientId)
                .requestEmail()
                .build();

        // 创建 Google 登录客户端
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

    }
    public void login(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void handleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            Log.w(Constants.TAG, " GoogleLogin Google sign in failed", e);
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(Constants.TAG, "GoogleLogin firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // 登录成功
                        Log.d(Constants.TAG, "GoogleLogin signInWithCredential:success");
                        // 可以在这里获取用户信息或者跳转到应用的下一个界面
                        HiUser user=new HiUser();
                        listener.onLoginSuccess(user);
                        // FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // 登录失败
                        listener.onLoginFailed(task.hashCode(),task.getException().toString());
                        Log.w(Constants.TAG, "GoogleLogin signInWithCredential:failure", task.getException());
                        Toast.makeText(mContext, "GoogleLogin Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
