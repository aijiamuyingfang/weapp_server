package android.os;

/**
 * [描述]:
 * <p>
 * 该接口是为了能够在非android环境上能使用cn.aijiamuyingfang.commons.domain的适配类
 * </p>
 * 
 * @version 1.0.0
 * @author shiweideyouxiang@sina.cn
 * @date 2018-07-25 18:58:53
 */
public interface Parcelable {
  public static interface Creator<T> {
    public abstract T createFromParcel(android.os.Parcel source);

    public abstract T[] newArray(int size);
  }

  public abstract int describeContents();

  public abstract void writeToParcel(android.os.Parcel dest, int flags);

}
