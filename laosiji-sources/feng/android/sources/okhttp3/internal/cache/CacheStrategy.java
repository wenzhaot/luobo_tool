package okhttp3.internal.cache;

import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.Constants;
import com.umeng.message.util.HttpRequest;
import javax.annotation.Nullable;
import okhttp3.Request;
import okhttp3.Response;

public final class CacheStrategy {
    @Nullable
    public final Response cacheResponse;
    @Nullable
    public final Request networkRequest;

    CacheStrategy(Request networkRequest, Response cacheResponse) {
        this.networkRequest = networkRequest;
        this.cacheResponse = cacheResponse;
    }

    public static boolean isCacheable(Response response, Request request) {
        switch (response.code()) {
            case 200:
            case 203:
            case 204:
            case 300:
            case Constants.COMMAND_STOP_FOR_ELECTION /*301*/:
            case 308:
            case 404:
            case 405:
            case 410:
            case 414:
            case 501:
                break;
            case ErrorCode.DM_DEVICEID_INVALID /*302*/:
            case 307:
                if (response.header(HttpRequest.HEADER_EXPIRES) == null && response.cacheControl().maxAgeSeconds() == -1 && !response.cacheControl().isPublic() && !response.cacheControl().isPrivate()) {
                    return false;
                }
            default:
                return false;
        }
        return (response.cacheControl().noStore() || request.cacheControl().noStore()) ? false : true;
    }
}
