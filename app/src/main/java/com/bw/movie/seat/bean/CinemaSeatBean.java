package com.bw.movie.seat.bean;

/**
 *
 * @描述 选座的bean
 *
 * @创建日期 2019/2/12 20:43
 *
 */
public class CinemaSeatBean {

    private int cinemasId;
    private String MovieName;
    private String beginTime;
    private String endTime;
    private String hall;
    private int seatsTotal;

    public CinemaSeatBean(int cinemasId, String movieName, String beginTime, String endTime, String hall, int seatsTotal) {
        this.cinemasId = cinemasId;
        MovieName = movieName;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.hall = hall;
        this.seatsTotal = seatsTotal;
    }

    public int getCinemasId() {
        return cinemasId;
    }

    public String getMovieName() {
        return MovieName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getHall() {
        return hall;
    }

    public int getSeatsTotal() {
        return seatsTotal;
    }
}
