package com.qiniu.android.bigdata.pipeline;

import com.qiniu.android.bigdata.Configuration;
import com.qiniu.android.http.Client;
import com.qiniu.android.http.CompletionHandler;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.utils.StringMap;
import com.qiniu.android.utils.StringUtils;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public final class Pipeline {
    private static final String HTTPHeaderAuthorization = "Authorization";
    private static final String TEXT_PLAIN = "text/plain";
    private final Client client = new Client(this.config.proxy, this.config.connectTimeout, this.config.responseTimeout, null, null);
    private final Configuration config;

    public interface PumpCompleteHandler {
        void complete(ResponseInfo responseInfo);
    }

    public Pipeline(Configuration config) {
        this.config = Configuration.copy(config);
    }

    public <V> void pump(String repo, Map<String, V> data, String token, PumpCompleteHandler handler) {
        StringBuilder b = new StringBuilder();
        Points.formatPoint((Map) data, b);
        send(repo, b, token, handler);
    }

    public void pump(String repo, Object data, String token, PumpCompleteHandler handler) {
        StringBuilder b = new StringBuilder();
        Points.formatPoint(data, b);
        send(repo, b, token, handler);
    }

    public <V> void pumpMulti(String repo, Map<String, V>[] data, String token, PumpCompleteHandler handler) {
        send(repo, Points.formatPoints((Map[]) data), token, handler);
    }

    public void pumpMultiObjects(String repo, Object[] data, String token, PumpCompleteHandler handler) {
        send(repo, Points.formatPoints(data), token, handler);
    }

    public <V> void pumpMultiObjects(String repo, List<V> data, String token, PumpCompleteHandler handler) {
        send(repo, Points.formatPointsObjects(data), token, handler);
    }

    public <V> void pumpMulti(String repo, List<Map<String, V>> data, String token, PumpCompleteHandler handler) {
        send(repo, Points.formatPoints((List) data), token, handler);
    }

    private void send(String repo, StringBuilder builder, String token, final PumpCompleteHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("no CompletionHandler");
        } else if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("no token");
        } else if (StringUtils.isBlank(repo)) {
            throw new IllegalArgumentException("no repo");
        } else {
            byte[] data = builder.toString().getBytes();
            StringMap headers = new StringMap();
            headers.put("Authorization", token);
            headers.put("Content-Type", TEXT_PLAIN);
            this.client.asyncPost(url(repo), data, headers, null, (long) data.length, null, new CompletionHandler() {
                public void complete(ResponseInfo info, JSONObject response) {
                    handler.complete(info);
                }
            }, null);
        }
    }

    private String url(String repo) {
        return this.config.pipelineHost + "/v2/repos/" + repo + "/data";
    }
}
