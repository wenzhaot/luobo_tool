package com.feng.car.utils;

import android.text.TextUtils;
import com.feng.car.entity.audio.AudioRecordInfo;
import java.util.ArrayList;
import java.util.List;

public class AudioRecordInfoManager {
    private static AudioRecordInfoManager mInstance;
    private List<AudioRecordInfo> mRecordList = new ArrayList();

    private AudioRecordInfoManager() {
    }

    public static AudioRecordInfoManager getInstance() {
        if (mInstance == null) {
            mInstance = new AudioRecordInfoManager();
        }
        return mInstance;
    }

    public void addRecord(AudioRecordInfo audioRecordInfo) {
        if (this.mRecordList.size() == 5) {
            this.mRecordList.remove(0);
            this.mRecordList.add(audioRecordInfo);
            return;
        }
        this.mRecordList.add(audioRecordInfo);
    }

    public AudioRecordInfo getRecord(String hash) {
        for (AudioRecordInfo audioRecordInfo : this.mRecordList) {
            if (TextUtils.equals(audioRecordInfo.viewPointVoiceHash, hash)) {
                return audioRecordInfo;
            }
        }
        return null;
    }

    public void updatePosition(String hash, int position) {
        getRecord(hash).voiceRecordPosition = position;
    }

    public void removeRecord(String hash) {
        try {
            this.mRecordList.remove(getRecord(hash));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clear() {
        this.mRecordList.clear();
    }
}
