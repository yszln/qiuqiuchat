package com.yszln.lib.network;

import android.text.TextUtils;

import com.yszln.lib.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

class LogInterceptor implements Interceptor {
    private Builder mBuilder;

    private LogInterceptor(Builder builder) {
        this.mBuilder = builder;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        // 请求
        Request request = chain.request();

        // 增加头部信息
        if (mBuilder.getHeaders().size() > 0) {
            Request.Builder requestBuilder = request.newBuilder();
            //tokens
            // 遍历并添加
            Headers headers = mBuilder.getHeaders();
            Set<String> names = headers.names();
            for (String name : names) {
                String value = headers.get(name);
                if (null != value) {
                    requestBuilder.addHeader(name, value);
                }
            }
            request = requestBuilder.build();
        }

        // 请求体
        RequestBody requestBody = request.body();

        MediaType rContentType = null;
        if (null != requestBody) {
            rContentType = requestBody.contentType();
        }

        String rSubtype = null;
        if (null != rContentType) {
            rSubtype = rContentType.subtype();
            // form-data
            // x-www-form-urlencoded
//            LogUtil.eSuper(rSubtype);
        }

        if ((null != rSubtype) && (rSubtype.contains("json")
                || rSubtype.contains("xml")
                || rSubtype.contains("plain")
                || rSubtype.contains("html")
                || rSubtype.contains("form"))) {
            printJsonRequest(mBuilder, request);
        } else {
            printFileRequest(mBuilder, request);
        }

        // 记录开始时间
        long st = System.nanoTime();
        // 响应
        Response response = chain.proceed(request);

        long chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st);
        String header = response.headers().toString();
        int code = response.code();
        boolean isSuccessful = response.isSuccessful();
        ResponseBody responseBody = response.body();

        if (null != responseBody) {

            MediaType contentType = responseBody.contentType();
            String subtype = null;
            if (contentType != null) {
                subtype = contentType.subtype();
            }

            if (subtype != null && (subtype.contains("json")
                    || subtype.contains("xml")
                    || subtype.contains("plain")
                    || subtype.contains("html"))) {

                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);// Buffer the entire body.
                Buffer buffer = source.buffer();

                String bodyString = buffer.clone().readUtf8();// responseBody.string();
                printJsonResponse(mBuilder, chainMs, isSuccessful, code, header, bodyString, request.url());
                // body = ResponseBody.create(contentType, bodyString);
            } else {
                printFileResponse(mBuilder, chainMs, isSuccessful, code, header, request.url());
            }
        }
        return response;
    }

    public static class Builder {

        private String TAG = "LoggingE";
        private boolean isDebug;
        private String requestTag;
        private String responseTag;
        private Headers.Builder builder;

        public Builder() {
            builder = new Headers.Builder();
        }

        Headers getHeaders() {
            return builder.build();
        }

        String getTag(boolean isRequest) {
            if (isRequest) {
                return TextUtils.isEmpty(requestTag) ? TAG : requestTag;
            } else {
                return TextUtils.isEmpty(responseTag) ? TAG : responseTag;
            }
        }

        /**
         * @param name  Filed
         * @param value Value
         * @return Builder
         * Add ic_card_bag field with the specified value
         */
        public Builder addHeader(String name, String value) {
            builder.set(name, value);
            return this;
        }

        /**
         * Set request and response each log tag
         *
         * @param tag general log tag
         * @return Builder
         */
        public Builder tag(String tag) {
            TAG = tag;
            return this;
        }

        /**
         * Set request log tag
         *
         * @param tag request log tag
         * @return Builder
         */
        public Builder setRequestTag(String tag) {
            this.requestTag = tag;
            return this;
        }

        /**
         * Set response log tag
         *
         * @param tag response log tag
         * @return Builder
         */
        public Builder setResponseTag(String tag) {
            this.responseTag = tag;
            return this;
        }

        /**
         * @param isDebug set can sending log output
         * @return Builder
         */
        public Builder isDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public LogInterceptor build() {
            return new LogInterceptor(this);
        }
    }

    // ---------------------------------------------------------------------------------------------

    private static final int JSON_INDENT = 3;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String[] OMITTED_REQUEST = {LINE_SEPARATOR, "Omitted request body"};
    private static final String[] OMITTED_RESPONSE = {LINE_SEPARATOR, "Omitted response body"};
    private static final String DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR;
    private static final int MAX_LONG_SIZE = 180;
    private static final String N = "\n";
    private static final String T = "\t";

    private boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || N.equals(line) || T.equals(line) || TextUtils.isEmpty(line.trim());
    }

    private void printJsonRequest(LogInterceptor.Builder builder, Request request) {
        // String requestBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + bodyToString(request);
        String body = bodyToString(request).replaceAll("%20", "");
        if (!body.startsWith("--")) {
            try {
                /**--------------------------------------------解决%报错的问题-------------------------------------------**/
                body = body.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                /**--------------------------------------------2019-09-16-------------------------------------------**/
                body = URLDecoder.decode(body, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String requestBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + body;
        String tag = builder.getTag(true);
        showLog(tag, "╔══════ 请求 ══════════════════════════════════════════════════════════════════════════════");
        logLines(tag, getRequest(request));
        logLines(tag, requestBody.split(LINE_SEPARATOR));
        showLog(tag, "╚═════════════════════════════════════════════════════════════════════════════════════════");
    }

    private void printFileRequest(LogInterceptor.Builder builder, Request request) {
        String tag = builder.getTag(true);
        showLog(tag, "╔══════ 请求 ══════════════════════════════════════════════════════════════════════════════");
        logLines(tag, getRequest(request));
        logLines(tag, OMITTED_REQUEST);
        showLog(tag, "╚═════════════════════════════════════════════════════════════════════════════════════════");
    }

    private void printJsonResponse(LogInterceptor.Builder builder, long chainMs, boolean isSuccessful,
                                   int code, String headers, String bodyString, HttpUrl url) {
        String responseBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + getJsonString(bodyString);
        String tag = builder.getTag(false);
        showLog(tag, "╔══════ 响应 ══════════════════════════════════════════════════════════════════════════════");
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, url));
        logLines(tag, responseBody.split(LINE_SEPARATOR));
        showLog(tag, "║ ");
        showLog(tag, "║ " + bodyString.replaceAll(LINE_SEPARATOR, ""));
        showLog(tag, "╚═════════════════════════════════════════════════════════════════════════════════════════");
    }

    private void printFileResponse(LogInterceptor.Builder builder, long chainMs, boolean isSuccessful,
                                   int code, String headers, HttpUrl url) {
        String tag = builder.getTag(false);
        showLog(tag, "╔══════ 响应 ══════════════════════════════════════════════════════════════════════════════");
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, url));
        logLines(tag, OMITTED_RESPONSE);
        showLog(tag, "╚═════════════════════════════════════════════════════════════════════════════════════════");
    }

    private String[] getRequest(Request request) {
        String message;
        String header = request.headers().toString();
        boolean loggableHeader = true;
        message = request.url() + "\u3000" + request.method() + DOUBLE_SEPARATOR +
                (isEmpty(header) ? "" : loggableHeader ? "Headers:" + LINE_SEPARATOR + dotHeaders(header) : "");
        return message.split(LINE_SEPARATOR);
    }

    private String[] getResponse(String header, long tookMs, int code, boolean isSuccessful, HttpUrl url) {
        /*String message;
        boolean loggableHeader = level == Level.HEADERS || level == Level.BASIC;
        String segmentString = slashSegments(segments);
        message = ((!StringUtils.isEmpty(segmentString) ? segmentString + " - " : "") + "is success : "
                + isSuccessful + " - " + "Received in: " + tookMs + "ms" + DOUBLE_SEPARATOR + "Status Code: " +
                code + DOUBLE_SEPARATOR + (isEmpty(header) ? "" : loggableHeader ? "Headers:" + LINE_SEPARATOR +
                dotHeaders(header) : ""));
        return message.split(LINE_SEPARATOR);*/
        String message;
        message = url + "\u3000" + code + "\u3000" + tookMs + "ms";// + DOUBLE_SEPARATOR +
        // (isEmpty(header) ? "" : "Headers:" + LINE_SEPARATOR + dotHeaders(header));
        return message.split(LINE_SEPARATOR);
    }

    /*private String slashSegments(List<String> segments) {
        StringBuilder segmentString = new StringBuilder();
        for (String segment : segments) {
            segmentString.append("/").append(segment);
        }
        return segmentString.toString();
    }*/

    private String dotHeaders(String header) {
        String[] headers = header.split(LINE_SEPARATOR);
        StringBuilder builder = new StringBuilder();
        for (String item : headers) {
            builder.append("- ").append(item).append("\n");
        }
        return builder.toString();
    }

    private void logLines(String tag, String[] lines) {
        for (String line : lines) {
            int lineLength = line.length();
            for (int i = 0; i <= lineLength / MAX_LONG_SIZE; i++) {
                int start = i * MAX_LONG_SIZE;
                int end = (i + 1) * MAX_LONG_SIZE;
                end = end > line.length() ? line.length() : end;
                showLog(tag, "║ " + line.substring(start, end));
            }
        }
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            if (null == copy.body())
                return "";
            copy.body().writeTo(buffer);
            return getJsonString(buffer.readUtf8());
        } catch (final IOException e) {
            return "{\"err\": \"" + e.getMessage() + "\"}";
        }
    }

    private String getJsonString(String msg) {
        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }
        return message;
    }

    /**
     * 显示Log
     */
    private void showLog(String tab, String msg) {
        int random = (int) ((Math.random() * 9 + 1) * 10000);
        LogUtil.e(random + "\u3000" + tab, msg);
    }
}
