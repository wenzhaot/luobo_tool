package anet.channel.flow;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.util.ALog;
import com.taobao.analysis.FlowCenter;

/* compiled from: Taobao */
public class a implements INetworkAnalysis {
    private boolean a;

    public a() {
        try {
            Class.forName("com.taobao.analysis.FlowCenter");
            this.a = true;
        } catch (Exception e) {
            this.a = false;
            ALog.e("DefaultNetworkAnalysis", "no NWNetworkAnalysisSDK sdk", null, new Object[0]);
        }
    }

    public void commitFlow(b bVar) {
        if (this.a) {
            FlowCenter.getInstance().commitFlow(GlobalAppRuntimeInfo.getContext(), bVar.a, bVar.b, bVar.c, bVar.d, bVar.e);
        }
    }
}
