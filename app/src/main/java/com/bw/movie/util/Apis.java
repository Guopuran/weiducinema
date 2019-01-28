package com.bw.movie.util;

/**
 *
 * @描述 接口存放
 *
 * @创建日期 2019/1/24 16:34
 *
 */
public class Apis {
   //登录的接口
   public static String LOGIN_URL="user/v1/login";
   //电影详情的接口
   public static String SELECT_MOVIE_DETAILS="movie/v1/findMoviesDetail?movieId=%s";
   //注册的接口
   public static String REGISTER_URL="user/v1/registerUser";
   //电影轮播的接口
    public static String MOVIEBAANNER_URL="movie/v1/findReleaseMovieList?page=%d&count=%d";
   //热门电影的接口
   public static  String MOVIEHOT_URL="movie/v1/findHotMovieList?page=%d&count=%d";
   //即将上映的接口
   public static String MOVIEWWILL_URL="movie/v1/findComingSoonMovieList?page=%d&count=%d";
   //根据电影ID查询电影信息
    public static String SELECT_ID_MOVIE_DETAILS="movie/v1/findMoviesById?movieId=%s";
  //推荐影院的接口
    public static  String  CONEMARECOMMEND_URL="cinema/v1/findRecommendCinemas?page=%d&count=%d";
  //附近影院的接口
    public static String CONEMARENEAR_URL="cinema/v1/findNearbyCinemas?page=%d&count=%d";
    //关注的接口
    public static  String MOVIEISFOLLOW_URL="movie/v1/verify/followMovie?movieId=%d";
    //关注的接口
    public static  String MOVIENOFOLLOW_URL="movie/v1/verify/cancelFollowMovie?movieId=%d";
}