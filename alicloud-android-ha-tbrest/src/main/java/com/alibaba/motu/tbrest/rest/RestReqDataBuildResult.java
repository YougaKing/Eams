package com.alibaba.motu.tbrest.rest;

import java.util.Map;

public class RestReqDataBuildResult {
    String mPostUrl;
    Map<String, Object> postReqData;

    public void setReqUrl(String aUrl) {
        this.mPostUrl = aUrl;
    }

    public String getReqUrl() {
        return this.mPostUrl;
    }

    public void setPostReqData(Map<String, Object> postReqData2) {
        this.postReqData = postReqData2;
    }

    public Map<String, Object> getPostReqData() {
        return this.postReqData;
    }
}
