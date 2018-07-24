package cn.aijiamuyingfang.client.rest.bean;

import cn.aijiamuyingfang.client.rest.converter.EnumRetrofitConverterFactory;
import cn.aijiamuyingfang.commons.utils.StringUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * [描述]:
 * <p>
 * HttpService注解的属性实例化工程
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-26 21:09:49
 */
public class HttpServiceBeanFactory {

  // key:请求地址 value:当前请求地址下class所对应的service（key:class value:service）
  private static final Map<String, HttpServiceBean> serviceBeans = new HashMap<>();

  /**
   * 读超时时间
   */
  private static final int READ_TIMEOUT = 60000;

  /**
   * 写超时时间
   */
  private static final int WRITE_TIMEOUT = 60000;

  /**
   * 连接超时时间
   */
  private static final int CONNECT_TIMEOUT = 60000;

  private HttpServiceBeanFactory() {
  }

  /**
   * 创建service服务实体
   * 
   * @param baseUrl
   * @param serviceClass
   * @param interceptorClasses
   * @return
   */
  public static Object putBean(String baseurl, Class<?> serviceClass, Class<?>... interceptorClasses) {
    if (StringUtils.isEmpty(baseurl)) {
      return null;
    }
    HttpServiceBean httpServiceBean = serviceBeans.get(baseurl);
    if (null == httpServiceBean) {
      httpServiceBean = new HttpServiceBean();

      GsonBuilder builder = new GsonBuilder();
      builder.registerTypeAdapter(Date.class, new TypeAdapter<Date>() {
        private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public void write(JsonWriter out, Date value) throws IOException {
          if (value != null) {
            out.value(dateFormatter.format(value));
          } else {
            out.value((String) null);
          }
        }

        @Override
        public Date read(JsonReader in) throws IOException {
          if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
          }
          String time = in.nextString();
          try {
            return dateFormatter.parse(time);
          } catch (ParseException e) {
            return null;
          }
        }

      });

      Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl).client(getOkHttpClient(baseurl, interceptorClasses))
          .addConverterFactory(ScalarsConverterFactory.create())
          .addConverterFactory(GsonConverterFactory.create(builder.create()))
          .addConverterFactory(new EnumRetrofitConverterFactory()).build();
      httpServiceBean.setRetrofit(retrofit);
      serviceBeans.put(baseurl, httpServiceBean);
    }

    Retrofit retrofit = httpServiceBean.getRetrofit();

    Object service = retrofit.create(serviceClass);
    httpServiceBean.getServices().put(serviceClass, service);
    return service;
  }

  /**
   * 获取OkHttpClient
   * 
   * @param baseurl
   * @param interceptorClasses
   * @return
   */
  private static OkHttpClient getOkHttpClient(String baseurl, Class<?>... interceptorClasses) {
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
    String hostname = baseurl.replace("https://", "").replace("/", "").split(":")[0];
    // 支持https,添加证书指纹,验证域名
    clientBuilder
        .certificatePinner(
            new CertificatePinner.Builder().add(hostname, "sha256/ws7jjHqFe0uRilFM2Rmby01kpiLy7LFiQLhQz4ntLWk=")
                .add(hostname, "sha256/jzqM6/58ozsPRvxUzg0hzjM+GcfwhTbU/G0TCDvL7hU=")
                .add(hostname, "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=").build())
        .hostnameVerifier((str, session) -> hostname.equals(str));

    // 添加服务拦截器实例
    for (Class<?> interceptorClass : interceptorClasses) {
      if (Interceptor.class.isAssignableFrom(interceptorClass)) {
        try {
          clientBuilder.addInterceptor((Interceptor) interceptorClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
        }
      }
    }

    return clientBuilder.build();
  }
}