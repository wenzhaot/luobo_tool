package com.umeng.socialize.media;

import com.umeng.socialize.sina.message.BaseRequest;
import com.umeng.socialize.sina.message.BaseResponse;

public interface IWeiboHandler {

    public interface Request {
        void a(BaseRequest baseRequest);
    }

    public interface Response {
        void a(BaseResponse baseResponse);
    }
}
