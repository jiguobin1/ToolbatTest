package jiguobin.bc.com.toolbattest.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by acer-PC on 2017/4/24.
 */

public class ImageInfo {
    /**
     * title : 现在的饭肿么这么难做呀
     * thumburl : http://ww3.sinaimg.cn/large/e4e2bea6jw1er57vncbigj20h40ckt9e.jpg
     * sourceurl : http://down.laifudao.com/images/tupian/20154120422.jpg
     * height : 452
     * width : 616
     * class : 1
     * url : http://www.laifudao.com/tupian/43170.htm
     */

    private String title;
    private String thumburl;
    private String sourceurl;
    private String height;
    private String width;
    @SerializedName("class")
    private String classX;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
