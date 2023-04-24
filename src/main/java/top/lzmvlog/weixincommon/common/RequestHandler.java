package top.lzmvlog.weixincommon.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Map;
import java.util.Objects;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @since 2023-04-18
 */

public class RequestHandler {

    public static String getUrl(String uriTemplate, Map<String, ?> uriVariables) {
        return uriTemplate + mapToUrls(JSON.parseObject(JSON.toJSONString(uriVariables)));
    }

    /**
     * 将map参数转换url参数
     *
     * @param data map请求参数
     * @return String
     */
    private static String mapToUrls(JSONObject data) {
        return mapToUrlsObj(data);
    }

    /**
     * 将map参数转换url参数
     *
     * @param data map请求参数
     * @return String
     */
    private static String mapToUrlsObj(JSONObject data) {
        if (Objects.isNull(data)) {
            return "";
        }
        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        decodeObject(multiValueMap, data, "");
        StringBuilder builder = new StringBuilder("?");
        multiValueMap.forEach((k, v) -> {
            v.forEach(p -> {
                builder.append(k).append("=").append(p).append("&");
            });
        });
        int index = builder.lastIndexOf("&");
        if (index >= 0) {
            builder.deleteCharAt(index);
        }
        return builder.toString();
    }

    /**
     * 编码对象为URL参数
     *
     * @param multiValueMap URL参数map
     * @param data          请求数据
     * @param key           url key
     */
    private static void decodeObject(LinkedMultiValueMap<String, String> multiValueMap, Object data, String key) {
        if (Objects.isNull(data)) {
            return;
        }
        if (data instanceof JSONObject) {
            ((JSONObject) data).forEach((k, v) -> {
                if (v instanceof JSONObject || v instanceof JSONArray) {
                    decodeObject(multiValueMap, v, k);
                } else {
                    if (Objects.nonNull(v)) {
                        multiValueMap.add(k, v.toString());
                    }
                }
            });

        } else if (data instanceof JSONArray) {
            ((JSONArray) data).forEach(v -> {
                if (v instanceof JSONObject || v instanceof JSONArray) {
                    decodeObject(multiValueMap, v, key);
                } else {
                    if (Objects.nonNull(v)) {
                        multiValueMap.add(key, v.toString());
                    }
                }
            });
        } else {
            multiValueMap.add(key, data.toString());
        }
    }
}
