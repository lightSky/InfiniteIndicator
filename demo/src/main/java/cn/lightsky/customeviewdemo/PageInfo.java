package cn.lightsky.customeviewdemo;

/**
 * Created by xushuai on 2014/12/25.
 */
public class PageInfo {
    private String data;
    private String url;
    private Integer drawableRes;

    public PageInfo(String data, Integer drawableRes) {
        this.data = data;
        this.drawableRes = drawableRes;
    }

    public PageInfo(String data, String url) {
        this.data = data;
        this.url = url;
    }

    public Integer getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(Integer drawableRes) {
        this.drawableRes = drawableRes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
