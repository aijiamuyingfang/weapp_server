package cn.aijiamuyingfang.server.client.converter;

import cn.aijiamuyingfang.server.commons.domain.BaseEnum;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class EnumRetrofitConverterFactory extends Converter.Factory {

  private static class BaseEnumConverter implements Converter<BaseEnum, String> {
    @Override
    public String convert(BaseEnum value) throws IOException {
      return String.valueOf(value.getValue());
    }
  }

  @Override
  public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
    if (type instanceof Class && BaseEnum.class.isAssignableFrom((Class<?>) type)) {
      return new BaseEnumConverter();
    }
    return null;
  }

}