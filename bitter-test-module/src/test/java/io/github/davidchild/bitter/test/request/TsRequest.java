package io.github.davidchild.bitter.test.request;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.davidchild.bitter.test.Init.CreateBaseMockSchema;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public abstract class TsRequest extends CreateBaseMockSchema {

    protected String baseUrl = "";

    public String baseId = "{{base_id_admin_test1}}";

    protected HttpHeaders headers;

    private static RestTemplate restTemplate = new RestTemplate();

    static {

    }

    public void InitTsRequest(String url, String accessTokenFiledName, String userToken) {
        this.baseUrl = url;
        this.baseId = userToken;
        headers = new HttpHeaders();
        headers.set(accessTokenFiledName, this.baseId);
    }


    /**
     * @param method
     * @param restSuffix 请求url的后缀。如完成请求url是http://localhost:8701/test。则这里是test
     * @param params     get的传参或post/put的 body中的参数
     * @return
     */
    public String getResult(HttpMethod method, String restSuffix, Map<String, Object> params) {

        String finalUrl = baseUrl + restSuffix; //最终发起调用的url
        HttpEntity httpEntity = null;
        if (method == HttpMethod.GET && !params.isEmpty()) {
            finalUrl = bulidUrl(finalUrl, params);
            httpEntity = new HttpEntity(headers);
        }
        if (method == HttpMethod.POST) {
            headers.setContentType(APPLICATION_JSON);
            httpEntity = new HttpEntity(params, headers);
        }

        ResponseEntity<String> response = restTemplate.exchange(finalUrl,
                method,
                httpEntity,
                String.class, params);
        System.out.println(String.format("do Http ==> methodType:%s url:%s ", method.name(), finalUrl));
        return response.getBody();
    }

    private String bulidUrl(String originalUrl, Map<String, Object> params) {
        //url需要用占位符的方式
        MultiValueMap<String, String> urlParams = new LinkedMultiValueMap<>();
        params.forEach((key, value) -> {
            if (isArray(value)) {
                //
                int length = Array.getLength(value);//如果是数组 那么获取数组的长度
                for (int i = 0; i < length; i++)   //  进行循环
                {
                    Object o = Array.get(value, i);
                    urlParams.add(key, String.valueOf(o));
                }
            } else {
                urlParams.add(key, String.valueOf(value));
            }
        });
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(originalUrl);
        return builder.queryParams(urlParams).build().encode().toUri().toString();
    }

    /**
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
            return false;
        }
        return obj.getClass().isArray();
    }


    /**
     * 将对象转成TreeMap,属性名为key,属性值为value
     *
     * @param object 对象
     * @return
     * @throws IllegalAccessException
     */
    public Map<String, Object> objToMap(Object object) throws IllegalAccessException {

        Class clazz = object.getClass();
        Map<String, Object> treeMap = new HashMap<>();


        while (null != clazz.getSuperclass()) {
            Field[] declaredFields1 = clazz.getDeclaredFields();

            for (Field field : declaredFields1) {
                String name = field.getName();

                // 获取原来的访问控制权限
                boolean accessFlag = field.isAccessible();
                // 修改访问控制权限
                field.setAccessible(true);
                Object value = field.get(object);
                // 恢复访问控制权限
                field.setAccessible(accessFlag);

                if (null != value && StringUtils.isNotBlank(value.toString())) {
                    //如果是List,将List转换为json字符串
                    if (value instanceof List) {
                        value = JSON.toJSONString(value);
                    }
                    treeMap.put(name, value);
                }
            }

            clazz = clazz.getSuperclass();
        }
        return treeMap;
    }

    public void writeResult(String responseString) {
        System.out.println("  MockHttp Request Result Is：\r\n   " + responseString);
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


