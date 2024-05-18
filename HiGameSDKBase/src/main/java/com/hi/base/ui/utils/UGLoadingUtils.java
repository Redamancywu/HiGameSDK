//package com.hi.base.ui.utils;
//
//import android.content.Context;
//
//import com.hi.base.utils.ResourceUtils;
//
//
//public class UGLoadingUtils {
//
//    private static SimpleProgressDialog progressDialog;
//
//    public static void showLoading(Context context) {
//        showLoading(context, true);
//    }
//
//    public static void showLoading(Context context, boolean mask) {
//
//        try{
//            if (progressDialog != null) {
//                hideLoading();
//                progressDialog = null;
//            }
//            if(mask){
//                progressDialog = new SimpleProgressDialog(context, ResourceUtils.getResourceID(context, "R.style.ug_dialog_with_mask"));
//            }else{
//                progressDialog = new SimpleProgressDialog(context);
//            }
//
//            progressDialog.show();
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 隐藏进度对话框
//     */
//    public static void hideLoading() {
//        try{
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//}
