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
    //取消关注的接口
    public static  String MOVIENOFOLLOW_URL="movie/v1/verify/cancelFollowMovie?movieId=%d";
    //查询影片评论的接口
    public static String SELECT_REVIEW="movie/v1/findAllMovieComment?movieId=%s&page=%d&count=%d";
    //查询影片评论回复的接口
    public static String SELECT_COMMENT="movie/v1/findCommentReply?commentId=%d&page=%d&count=%d";
    //电影评论点赞的接口
    public static String NEXT_COMMENT="movie/v1/verify/movieCommentGreat?";
    //微信登录的接口
    public static String WEIXINLOGON_URL="user/v1/weChatBindingLogin";
    //影院的关注接口
    public static  String CINEMAISFOLLOW_URL="cinema/v1/verify/followCinema?cinemaId=%d";
    //影院取消关注的接口
    public static String CINEMANOFOLLOW_URL="cinema/v1/verify/cancelFollowCinema?cinemaId=%d";
    //电影详情的banner图
    public static String CINEMADETAILSBANNER_URL="movie/v1/findMovieListByCinemaId?cinemaId=%d";
    //电影详情里的排期
    public static String CINEMADDETAILSTIMELIST_URL="movie/v1/findMovieScheduleList?cinemasId=%d&movieId=%d";

    //添加用户对影片的评论
    public static String INSERT_COMMENT="movie/v1/verify/movieComment";
    //根据电影ID查询当前排片该电影的影院列表的接口
    public static String SELECT_ID_THEATRE="movie/v1/findCinemasListByMovieId?movieId=%s";
    //根据电影ID和影院ID查询电影排期列表
    public static String SELECT_CINEMA_MOVIE="movie/v1/findMovieScheduleList?cinemasId=%d&movieId=%d";
    //查询电影信息明细
    public static String SELECT_CINEMA_DEATILS="cinema/v1/findCinemaInfo?cinemaId=%d";
    //查询影院用户评论列表
    public static String SELECT_COMMENT_CINEMA="cinema/v1/findAllCinemaComment?cinemaId=%d&page=%d&count=%d";

}