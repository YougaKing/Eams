//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.monitor.procedure;

public class ProcedureConfig {
    private final IProcedure parent;
    private final boolean upload;
    private final boolean independent;
    private final boolean parentNeedStats;

    private ProcedureConfig(ProcedureConfig.Builder var1) {
        this.upload = var1.upload;
        this.independent = var1.independent;
        this.parent = var1.parent;
        this.parentNeedStats = var1.parentNeedStats;
    }

    public IProcedure getParent() {
        return this.parent;
    }

    public boolean isUpload() {
        return this.upload;
    }

    public boolean isIndependent() {
        return this.independent;
    }

    public boolean isParentNeedStats() {
        return this.parentNeedStats;
    }

    public static class Builder {
        private boolean upload;
        private boolean independent;
        private boolean parentNeedStats;
        private IProcedure parent;

        public Builder() {
        }

        public ProcedureConfig.Builder setParent(IProcedure var1) {
            this.parent = var1;
            return this;
        }

        public ProcedureConfig.Builder setUpload(boolean var1) {
            this.upload = var1;
            return this;
        }

        public ProcedureConfig.Builder setIndependent(boolean var1) {
            this.independent = var1;
            return this;
        }

        public ProcedureConfig.Builder setParentNeedStats(boolean var1) {
            this.parentNeedStats = var1;
            return this;
        }

        public ProcedureConfig build() {
            return new ProcedureConfig(this);
        }
    }
}
