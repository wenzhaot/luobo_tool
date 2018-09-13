package com.feng.library.okhttp.builder;

import com.feng.library.okhttp.request.PostFormRequest;
import com.feng.library.okhttp.request.RequestCall;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PostFormBuilder extends OkHttpRequestBuilder implements HasParamsable {
    private List<FileInput> files = new ArrayList();

    public static class FileInput {
        public File file;
        public String filename;
        public String key;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        public String toString() {
            return "FileInput{key='" + this.key + '\'' + ", filename='" + this.filename + '\'' + ", file=" + this.file + '}';
        }
    }

    public RequestCall build() {
        return new PostFormRequest(this.url, this.tag, this.params, this.headers, this.files).build();
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        this.files.add(new FileInput(name, filename, file));
        return this;
    }

    public PostFormBuilder url(String url) {
        this.url = url;
        return this;
    }

    public PostFormBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public PostFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public PostFormBuilder addParams(String key, String val) {
        if (this.params == null) {
            this.params = new LinkedHashMap();
        }
        this.params.put(key, val);
        return this;
    }

    public PostFormBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public PostFormBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap();
        }
        this.headers.put(key, val);
        return this;
    }

    public PostFormBuilder addFiles(String name, Map<String, File> fileMap) {
        for (String fileName : fileMap.keySet()) {
            this.files.add(new FileInput(name, fileName, (File) fileMap.get(fileName)));
        }
        return this;
    }
}
