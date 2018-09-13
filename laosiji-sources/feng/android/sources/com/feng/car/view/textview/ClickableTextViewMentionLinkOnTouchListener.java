package com.feng.car.view.textview;

import android.graphics.Color;
import android.view.View.OnTouchListener;

public class ClickableTextViewMentionLinkOnTouchListener implements OnTouchListener {
    private int color;
    private boolean find;

    public ClickableTextViewMentionLinkOnTouchListener(int color) {
        this.find = false;
        this.color = color;
    }

    public ClickableTextViewMentionLinkOnTouchListener() {
        this.find = false;
        this.color = Color.parseColor("#D4DCE7");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00b9  */
    public boolean onTouch(android.view.View r30, android.view.MotionEvent r31) {
        /*
        r29 = this;
        r26 = r30;
        r26 = (android.widget.TextView) r26;
        r10 = r26.getLayout();
        if (r10 != 0) goto L_0x000d;
    L_0x000a:
        r26 = 0;
    L_0x000c:
        return r26;
    L_0x000d:
        r26 = r31.getX();
        r0 = r26;
        r0 = (int) r0;
        r24 = r0;
        r26 = r31.getY();
        r0 = r26;
        r0 = (int) r0;
        r25 = r0;
        r0 = r25;
        r12 = r10.getLineForVertical(r0);
        r0 = r24;
        r0 = (float) r0;
        r26 = r0;
        r0 = r26;
        r14 = r10.getOffsetForHorizontal(r12, r0);
        r15 = r30;
        r15 = (android.widget.TextView) r15;
        r26 = r15.getText();
        r17 = android.text.SpannableString.valueOf(r26);
        r26 = r31.getActionMasked();
        switch(r26) {
            case 0: goto L_0x0046;
            case 1: goto L_0x00f4;
            case 2: goto L_0x0150;
            case 3: goto L_0x00f4;
            default: goto L_0x0043;
        };
    L_0x0043:
        r26 = 0;
        goto L_0x000c;
    L_0x0046:
        r26 = 0;
        r27 = r17.length();
        r28 = com.feng.car.view.textview.MyURLSpan.class;
        r0 = r17;
        r1 = r26;
        r2 = r27;
        r3 = r28;
        r26 = r0.getSpans(r1, r2, r3);
        r26 = (com.feng.car.view.textview.MyURLSpan[]) r26;
        r16 = r26;
        r16 = (com.feng.car.view.textview.MyURLSpan[]) r16;
        r8 = 0;
        r7 = 0;
        r13 = r16;
        r0 = r16;
        r0 = r0.length;
        r19 = r0;
        r21 = 0;
    L_0x006b:
        r0 = r21;
        r1 = r19;
        if (r0 >= r1) goto L_0x0094;
    L_0x0071:
        r22 = r13[r21];
        r0 = r17;
        r1 = r22;
        r9 = r0.getSpanStart(r1);
        r0 = r17;
        r1 = r22;
        r23 = r0.getSpanEnd(r1);
        if (r9 > r14) goto L_0x00ed;
    L_0x0085:
        r0 = r23;
        if (r14 > r0) goto L_0x00ed;
    L_0x0089:
        r26 = 1;
        r0 = r26;
        r1 = r29;
        r1.find = r0;
        r8 = r9;
        r7 = r23;
    L_0x0094:
        r18 = r10.getLineWidth(r12);
        r0 = r29;
        r0 = r0.find;
        r27 = r0;
        r0 = r24;
        r0 = (float) r0;
        r26 = r0;
        r26 = (r18 > r26 ? 1 : (r18 == r26 ? 0 : -1));
        if (r26 < 0) goto L_0x00f1;
    L_0x00a7:
        r26 = 1;
    L_0x00a9:
        r26 = r26 & r27;
        r0 = r26;
        r1 = r29;
        r1.find = r0;
        r0 = r29;
        r0 = r0.find;
        r26 = r0;
        if (r26 == 0) goto L_0x00e5;
    L_0x00b9:
        r26 = com.feng.car.view.textview.LongClickableLinkMovementMethod.getInstance();
        r0 = r26;
        r1 = r17;
        r2 = r31;
        r0.onTouchEvent(r15, r1, r2);
        r20 = new android.text.style.BackgroundColorSpan;
        r0 = r29;
        r0 = r0.color;
        r26 = r0;
        r0 = r20;
        r1 = r26;
        r0.<init>(r1);
        r26 = 18;
        r0 = r17;
        r1 = r20;
        r2 = r26;
        r0.setSpan(r1, r8, r7, r2);
        r0 = r17;
        r15.setText(r0);
    L_0x00e5:
        r0 = r29;
        r0 = r0.find;
        r26 = r0;
        goto L_0x000c;
    L_0x00ed:
        r21 = r21 + 1;
        goto L_0x006b;
    L_0x00f1:
        r26 = 0;
        goto L_0x00a9;
    L_0x00f4:
        r0 = r29;
        r0 = r0.find;
        r26 = r0;
        if (r26 == 0) goto L_0x0110;
    L_0x00fc:
        r26 = com.feng.car.view.textview.LongClickableLinkMovementMethod.getInstance();
        r0 = r26;
        r1 = r17;
        r2 = r31;
        r0.onTouchEvent(r15, r1, r2);
        r26 = com.feng.car.view.textview.LongClickableLinkMovementMethod.getInstance();
        r26.removeLongClickCallback();
    L_0x0110:
        r26 = 0;
        r27 = r17.length();
        r28 = android.text.style.BackgroundColorSpan.class;
        r0 = r17;
        r1 = r26;
        r2 = r27;
        r3 = r28;
        r26 = r0.getSpans(r1, r2, r3);
        r26 = (android.text.style.BackgroundColorSpan[]) r26;
        r6 = r26;
        r6 = (android.text.style.BackgroundColorSpan[]) r6;
        r4 = r6;
        r11 = r6.length;
        r9 = 0;
    L_0x012d:
        if (r9 >= r11) goto L_0x0139;
    L_0x012f:
        r5 = r4[r9];
        r0 = r17;
        r0.removeSpan(r5);
        r9 = r9 + 1;
        goto L_0x012d;
    L_0x0139:
        r0 = r29;
        r0 = r0.find;
        r26 = r0;
        if (r26 == 0) goto L_0x0146;
    L_0x0141:
        r0 = r17;
        r15.setText(r0);
    L_0x0146:
        r26 = 0;
        r0 = r26;
        r1 = r29;
        r1.find = r0;
        goto L_0x0043;
    L_0x0150:
        r0 = r29;
        r0 = r0.find;
        r26 = r0;
        if (r26 == 0) goto L_0x0043;
    L_0x0158:
        r26 = com.feng.car.view.textview.LongClickableLinkMovementMethod.getInstance();
        r0 = r26;
        r1 = r17;
        r2 = r31;
        r0.onTouchEvent(r15, r1, r2);
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.view.textview.ClickableTextViewMentionLinkOnTouchListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }
}
