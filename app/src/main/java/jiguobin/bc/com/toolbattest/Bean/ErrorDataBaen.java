package jiguobin.bc.com.toolbattest.Bean;

/**
 * Created by acer-PC on 2017/4/27.
 */

public class ErrorDataBaen {
    private String status;
    private String detail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ErrorDataBaen{" +
                "status='" + status + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}


