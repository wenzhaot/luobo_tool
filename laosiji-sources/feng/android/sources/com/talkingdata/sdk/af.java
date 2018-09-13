package com.talkingdata.sdk;

/* compiled from: td */
public class af {

    /* compiled from: td */
    public enum AccountType {
        ANONYMOUS(0),
        REGISTERED(1),
        SINA_WEIBO(2),
        QQ(3),
        QQ_WEIBO(4),
        ND91(5),
        WEIXIN(6),
        TYPE1(11),
        TYPE2(12),
        TYPE3(13),
        TYPE4(14),
        TYPE5(15),
        TYPE6(16),
        TYPE7(17),
        TYPE8(18),
        TYPE9(19),
        TYPE10(20);
        
        private final int index;

        private AccountType(int i) {
            this.index = i;
        }

        public int index() {
            return this.index;
        }
    }

    /* compiled from: td */
    public enum Gender {
        UNKNOW(0),
        MALE(1),
        FEMALE(2);
        
        private final int index;

        private Gender(int i) {
            this.index = i;
        }

        public int index() {
            return this.index;
        }
    }
}
