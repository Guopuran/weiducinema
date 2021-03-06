package com.bw.movie.details.bean;

import java.io.Serializable;
import java.util.List;
/**
 *
 * @描述 电影排期的bean
 *
 * @创建日期 2019/1/30 15:25
 *
 */
public class MovieScheduleBean implements Serializable {

    /**
     * result : [{"beginTime":"19:20","duration":"118分钟","endTime":"21:18","id":1,"screeningHall":"2号厅","seatsTotal":150,"seatsUseCount":50,"status":1},{"beginTime":"20:30","duration":"118分钟","endTime":"22:28","id":2,"screeningHall":"1号厅","seatsTotal":66,"seatsUseCount":15,"status":1},{"beginTime":"19:20","duration":"118分钟","endTime":"21:18","id":14,"screeningHall":"5号厅","seatsTotal":120,"seatsUseCount":70,"status":1}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * beginTime : 19:20
         * duration : 118分钟
         * endTime : 21:18
         * id : 1
         * screeningHall : 2号厅
         * seatsTotal : 150
         * seatsUseCount : 50
         * status : 1
         */

        private String beginTime;
        private String duration;
        private String endTime;
        private int id;
        private String screeningHall;
        private int seatsTotal;
        private int seatsUseCount;
        private int status;
        private double price;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getScreeningHall() {
            return screeningHall;
        }

        public void setScreeningHall(String screeningHall) {
            this.screeningHall = screeningHall;
        }

        public int getSeatsTotal() {
            return seatsTotal;
        }

        public void setSeatsTotal(int seatsTotal) {
            this.seatsTotal = seatsTotal;
        }

        public int getSeatsUseCount() {
            return seatsUseCount;
        }

        public void setSeatsUseCount(int seatsUseCount) {
            this.seatsUseCount = seatsUseCount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
