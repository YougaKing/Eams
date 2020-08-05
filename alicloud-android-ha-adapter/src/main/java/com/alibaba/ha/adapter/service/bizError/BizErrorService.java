//package com.alibaba.ha.adapter.service.bizError;
//
//import android.content.Context;
//import android.util.Log;
//import com.alibaba.ha.adapter.AliHaAdapter;
//import com.alibaba.ha.adapter.Sampling;
//import com.alibaba.ha.bizerrorreporter.BizErrorReporter;
//import com.alibaba.ha.bizerrorreporter.BizErrorSampling;
//
//public class BizErrorService {
//    private static boolean isValid;
//
//    static {
//        isValid = false;
//        try {
//            Class.forName("com.alibaba.ha.bizerrorreporter.BizErrorReporter");
//            isValid = true;
//        } catch (ClassNotFoundException e) {
//            isValid = false;
//        }
//    }
//
//    public static void sendBizError(Context context, BizErrorInfo bizErrorInfo) {
//        if (isValid && bizErrorInfo != null) {
//            BizErrorReporter.getInstance().send(context, bizErrorInfo);
//        }
//    }
//
//    public static void openSampling(Sampling sampling) {
//        if (isValid) {
//            try {
//                BizErrorReporter.getInstance().openSampling(BizErrorSampling.valueOf(sampling.name()));
//            } catch (Exception e) {
//                Log.e(AliHaAdapter.TAG, "open biz error sampling failure ", e);
//            }
//        }
//    }
//}
