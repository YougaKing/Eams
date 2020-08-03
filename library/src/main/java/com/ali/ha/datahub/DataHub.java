//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ali.ha.datahub;

import java.util.HashMap;

public class DataHub {
    private BizSubscriber mSubscriber;
    private DataHub.SubProcedure mSubProcedure;

    private DataHub() {
    }

    public static final DataHub getInstance() {
        return DataHub.SingleInstanceHolder.sInstance;
    }

    public void publish(String var1, HashMap<String, Object> var2) {
        if (null != this.mSubscriber) {
            this.mSubscriber.pub(var1, var2);
        }
    }

    public void publishABTest(String var1, HashMap<String, Object> var2) {
        if (null != this.mSubscriber) {
            this.mSubscriber.pubAB(var1, var2);
        }
    }

    public void onStage(String var1, String var2) {
        this.onStage(var1, var2, System.currentTimeMillis());
    }

    public void onStage(String var1, String var2, long var3) {
        if (null != this.mSubscriber) {
            this.mSubscriber.onStage(var1, var2, var3);
        }
    }

    public void setCurrentBiz(String var1, String var2) {
        if (null != this.mSubscriber) {
            this.mSubscriber.setMainBiz(var1, var2);
        }
    }

    public void setCurrentBiz(String var1) {
        if (null != this.mSubscriber) {
            this.mSubscriber.setMainBiz(var1, (String)null);
        }
    }

    public void onBizDataReadyStage() {
        if (null != this.mSubscriber) {
            this.mSubscriber.onBizDataReadyStage();
        }
    }

    public void init(BizSubscriber var1) {
        if (null == this.mSubscriber) {
            this.mSubscriber = var1;
            this.mSubProcedure = new DataHub.SubProcedure(this.mSubscriber);
        }

    }

    private DataHub.SubProcedure subProcedure() {
        if (null == this.mSubProcedure) {
            this.mSubProcedure = new DataHub.SubProcedure();
        }

        return this.mSubProcedure;
    }

    private static class SubProcedure {
        private BizSubscriber mSubscriber;

        private SubProcedure() {
        }

        private SubProcedure(BizSubscriber var1) {
            this.mSubscriber = var1;
        }

        public void onBegin(String var1) {
            if (this.mSubscriber == null) {
            }

        }

        public void onStage(String var1, String var2) {
            if (this.mSubscriber == null) {
            }

        }

        public void onEnd(String var1) {
            if (this.mSubscriber == null) {
            }

        }
    }

    private static final class SingleInstanceHolder {
        public static final DataHub sInstance = new DataHub();

        private SingleInstanceHolder() {
        }
    }
}
