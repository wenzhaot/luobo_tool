package anet.channel.strategy;

import anet.channel.strategy.k.a;

/* compiled from: Taobao */
class i implements Predicate<IPConnStrategy> {
    final /* synthetic */ a a;
    final /* synthetic */ String b;
    final /* synthetic */ ConnProtocol c;
    final /* synthetic */ StrategyList d;

    i(StrategyList strategyList, a aVar, String str, ConnProtocol connProtocol) {
        this.d = strategyList;
        this.a = aVar;
        this.b = str;
        this.c = connProtocol;
    }

    /* renamed from: a */
    public boolean apply(IPConnStrategy iPConnStrategy) {
        return iPConnStrategy.getPort() == this.a.a && iPConnStrategy.getIp().equals(this.b) && iPConnStrategy.protocol.equals(this.c);
    }
}
