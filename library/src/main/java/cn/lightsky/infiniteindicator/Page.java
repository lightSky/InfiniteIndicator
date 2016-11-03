package cn.lightsky.infiniteindicator;

/**
 * Created by xushuai on 2014/12/25.
 */
public class Page {
    public String data = "";
    public Object res;

    public Page(Object res) {
        this.res = res;
    }

    public Page(String data, Object res) {
        this.data = data;
        this.res = res;
    }

    public Page(String data, Object res, OnPageClickListener listener) {
        this.data = data;
        this.res = res;
    }
}
