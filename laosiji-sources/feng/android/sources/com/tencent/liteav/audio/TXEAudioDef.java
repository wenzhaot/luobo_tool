package com.tencent.liteav.audio;

import com.feng.car.utils.HttpConstant;
import com.taobao.accs.internal.b;
import com.umeng.socialize.bean.StatusCode;

public class TXEAudioDef {
    public static int TXE_AEC_NONE = 0;
    public static int TXE_AEC_SYSTEM = 1;
    public static int TXE_AEC_TRAE = 2;
    public static int TXE_AUDIO_COMMON_ERR_INVALID_PARAMS = b.ELE_ERROR_EXCEPTION;
    public static int TXE_AUDIO_COMMON_ERR_OK = 0;
    public static int TXE_AUDIO_JITTER_STATE_FIRST_LAODING = 0;
    public static int TXE_AUDIO_JITTER_STATE_FIRST_PLAY = 3;
    public static int TXE_AUDIO_JITTER_STATE_LOADING = 1;
    public static int TXE_AUDIO_JITTER_STATE_PLAYING = 2;
    public static int TXE_AUDIO_MODE_RECEIVER = 1;
    public static int TXE_AUDIO_MODE_SPEAKER = 0;
    public static int TXE_AUDIO_NOTIFY_AUDIO_INFO = 1;
    public static int TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT = -102;
    public static int TXE_AUDIO_PLAY_ERR_INVALID_STATE = HttpConstant.USER_BIND_SHOP;
    public static int TXE_AUDIO_PLAY_ERR_NOT_CREATE_JIT = -105;
    public static int TXE_AUDIO_PLAY_ERR_OK = 0;
    public static int TXE_AUDIO_PLAY_ERR_REPEAT_OPTION = -104;
    public static int TXE_AUDIO_PLAY_ERR_START_HW_DECODEC_FAILED = StatusCode.ST_CODE_SDK_NORESPONSE;
    public static int TXE_AUDIO_RECORD_ERR_CAPTURE_DATA_INVALID = -4;
    public static int TXE_AUDIO_RECORD_ERR_CUR_PLAYER_INVALID = -5;
    public static int TXE_AUDIO_RECORD_ERR_CUR_RECORDER_INVALID = -106;
    public static int TXE_AUDIO_RECORD_ERR_NOT_START = -3;
    public static int TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT = -1;
    public static int TXE_AUDIO_RECORD_ERR_OK = 0;
    public static int TXE_AUDIO_RECORD_ERR_REPEAT_OPTION = -2;
    public static int TXE_AUDIO_TYPE_AAC = 10;
    public static int TXE_AUDIO_TYPE_INVALID = 0;
    public static int TXE_AUDIO_TYPE_MP3 = 2;
    public static int TXE_AUDIO_TYPE_PCM = 1;
    public static int TXE_REVERB_TYPE_0 = 0;
    public static int TXE_REVERB_TYPE_1 = 1;
    public static int TXE_REVERB_TYPE_2 = 2;
    public static int TXE_REVERB_TYPE_3 = 3;
    public static int TXE_REVERB_TYPE_4 = 4;
    public static int TXE_REVERB_TYPE_5 = 5;
    public static int TXE_REVERB_TYPE_6 = 6;
    public static int TXE_REVERB_TYPE_7 = 7;
}
