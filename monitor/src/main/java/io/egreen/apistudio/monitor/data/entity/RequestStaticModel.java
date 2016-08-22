package io.egreen.apistudio.monitor.data.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by dewmal on 8/20/16.
 */
@Table(name = "monitor_resource_model")
public class RequestStaticModel {


    @Id
    private String requestId;
    private Date requestStartTime;
    private Date requestEndTime;
    private long totalByteLength;
    private int stateCode;
    private String requestPath;

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Date getRequestStartTime() {
        return requestStartTime;
    }

    public void setRequestStartTime(Date requestStartTime) {
        this.requestStartTime = requestStartTime;
    }

    public Date getRequestEndTime() {
        return requestEndTime;
    }

    public void setRequestEndTime(Date requestEndTime) {
        this.requestEndTime = requestEndTime;
    }

    public long getTotalByteLength() {
        return totalByteLength;
    }

    public void setTotalByteLength(long totalByteLength) {
        this.totalByteLength = totalByteLength;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }
}
