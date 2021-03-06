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
    //关注的影院
    public static String MY_FOLLLOW_CINEMA="cinema/v1/verify/findCinemaPageList?page=%d&count=%d";
    //关注的电影
    public static String MY_FOLLOW_MOVIE="movie/v1/verify/findMoviePageList?page=%d&count=%d";
    //意见反馈的接口
    public static String MY_SUGGESTIN="tool/v1/verify/recordFeedBack";
    //修改密码的接口
    public static String MY_UPDATE_PASSWORD="user/v1/verify/modifyUserPwd";
    //查询影院用户评论列表
    public static String SELECT_COMMENT_CINEMA="cinema/v1/findAllCinemaComment?cinemaId=%d&page=%d&count=%d";
    //签到的网络接口
    public static String MY_SING="user/v1/verify/userSignIn";
    //查询用户信息
    public static  String SEARCHMESSAGE="user/v1/verify/getUserInfoByUserId";
    //购票下单
    public static  String INDENTPAY="movie/v1/verify/buyMovieTicket";
    //支付
    public static  String PAY="movie/v1/verify/pay";
    //上传头像
    public static  String HEAD_IMAGE="user/v1/verify/uploadHeadPic";
    //版本更新的接口
    public static  String SUGGESTIO_URL="tool/v1/findNewVersion";
    //用户查询购票记录
    public static String MY_TICKETRECROD="user/v1/verify/findUserBuyTicketRecordList?page=%d&count=%d&status=%d";
    //消息读取
    public static  String SYSTEM_MESSAGE_LIST="tool/v1/verify/findAllSysMsgList?page=%d&count=%d";
    //改变读取状态
    public static String SYSTEM_MESSAGE_READ="tool/v1/verify/changeSysMsgStatus?id=%d";
    //系统消息未读数量
    public static String SYSTEM_MESSAGE_COUNT="tool/v1/verify/findUnreadMessageCount";
    //修改个人信息
    public  static  String UPDATEUSERMESSAGE="user/v1/verify/modifyUserInfo";
    //上传消息推送使用的token
    public  static  String TOKEN="tool/v1/verify/uploadPushToken";

}