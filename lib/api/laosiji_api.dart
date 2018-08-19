import 'package:dio/dio.dart';
import 'dart:convert';
import 'dart:io';
import 'package:luobo_tool/model/movie.dart';

//class BmobResults {
//  final List<Movie> results;
//
//  const BmobResults({this.results});
//
//  factory BmobResults.fromJson(Map<String, dynamic> parsedJson){
//    var resultsFromJson  = parsedJson['results'];
//    List<Movie> movieList = [];
//    for (var movieItem in resultsFromJson){
//      movieList.add(new Movie.fromJson(movieItem));
//    }
//
//    return BmobResults(
//        results: movieList
//    );
//  }
//}


class LaosijiApi {

  final Options options = Options(
      baseUrl: 'http://api.laosiji.com',
      headers: {'Cookie':'__DAYU_PP=uimzr7Yqe3b2nqfJnqeE2b1f3a7e796c'},
      contentType: ContentType.parse("application/x-www-form-urlencoded")
  );

  getMovies() async {

    Map<String, dynamic> getParams = {
      'md5' : '0f4aaa617872383aa30fb2023d844534',
      'production' : 'laosiji',
      'production_version' : 'ios2.9',
      'systemtag' : 5,
      'mac_address' : '02:00:00:00:00:00',
      'appid' : 'ios',
      'osversion' : '10.3.3',
      'chargestatus' :	1,
      'latitude' : 39.93043518066406,
      'longitude' :	116.5925598144531,
      'country' :	'中国',
      'province' : '北京市',
      'city' : '北京市',
      'district' : '朝阳区',
      'electricity' :	'100%',
      'imsi' : '(null)(null)',
      'impresstime' :	2147483647,
      'ip' : '192.168.1.104',
      'language' : 'zh',
      'platform' : 2,
      'version' :	'2.2.3',
      'screen_width' : 320,
      'screen_hight' : 568,
      'brand' :	'苹果',
      'devicename' : 'iPhone5,2',
      'deviceid' : '32BCDE01-CB0F-4526-9E7E-AC175DE3F528',
      'imei' : '32BCDE01-CB0F-4526-9E7E-AC175DE3F528',
      'local' :	'GMT+8',
      'lan' :	'wifi',
      'wifi' : 'tanwenzhao',
      'wifi_address' : '192.168.1.1',
      'uid' :	1139667,
      'operator' : 1,
      'landscape' :	0,
      'referer' : 'app_search?keyword=萝卜&type=1',
      'page' : 'app_profile?userid=7613',
      'launchidentifying' :	'C3C347D3-BDC5-4F37-A2D2-73273D13993A',
      'switchidentifying' :	'2D016698-6B1F-4410-A2DE-6D7DDD380F55',
      'pageidentifying' :	'9AB6B6F4-95C5-46A5-BAFC-4D14CDD87903'
    };

    Map<String, dynamic> postParams = {
      "userid":"7613",
      "devicemark":"62fd2fc6286fe5d9c5651b79a7e88756c7f33a5a7c9c8a3cb635cd479b571322",
      "page":"2",
      "orderid":"1",
      "usertoken":"MmhOTkdFQU14TVRFTUZpTU1EemsyWmtOalltbXpOMWpZOHpaRGlXTmhNV1lXTkVNaE1XTkZGalpqVmpWbU5ZTVdOakFpTlRNbU0xTVQxR1kwemlPVEJNWWhZakZ6QTVNempEWmtOTk5EZ014M05EZGlNMlp6Z0RkaldNTTJZall4T1QyeE16TTJNV0ppVHhOMk1NeD0xTQ==",
      "time":"2018-08-19 20:06:03",
      "issmalltopic":"-1"
    };

    String getString = Transformer.urlEncodeMap(getParams);
    String postJsonString = JSON.encode(postParams);

    print(postJsonString);

    Dio dio = Dio(options);
    Response response = await dio.post("/sns/snslist/5/3?$getString", data: {'chk':'e50eae1233e775a59e063597c3552c6e', 'parameter':postJsonString});

    print(response.data);
  }

}