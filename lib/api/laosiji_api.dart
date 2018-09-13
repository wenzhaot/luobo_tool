import 'package:dio/dio.dart';
import 'package:intl/intl.dart';
import 'package:crypto/crypto.dart';
import 'dart:convert';
import 'dart:io';
import 'package:luobo_tool/model/movie.dart';

class LaosijiResults {
  final List<Movie> results;

  const LaosijiResults({this.results});

  factory LaosijiResults.fromJson(Map<String, dynamic> parsedJson){
    var resultsFromJson  = parsedJson['list'];
    List<Movie> movieList = [];
    for (var movieItem in resultsFromJson){
      movieList.add(new Movie.fromLaosijiJson(movieItem));
    }

    return LaosijiResults(
        results: movieList
    );
  }
}



class LaosijiApi {

  final Options options = Options(
      baseUrl: 'http://api.laosiji.com',
      headers: {'Cookie':'__DAYU_PP=uimzr7Yqe3b2nqfJnqeE2b1f3a7e796c', 'User-Agent' : 'Dalvik/1.6.0 (Linux; U; Android 8.1.0; BLA-AL00 Build/HUAWEI)'},
      contentType: ContentType.parse("application/x-www-form-urlencoded")
  );

  getMovies() async {

//    Map<String, dynamic> getParams = {
//      'md5' : '0f4aaa617872383aa30fb2023d844534',
//      'production' : 'laosiji',
//      'production_version' : 'ios2.9',
//      'systemtag' : 5,
//      'mac_address' : '02:00:00:00:00:00',
//      'appid' : 'ios',
//      'osversion' : '10.3.3',
//      'chargestatus' :	1,
//      'latitude' : 39.93043518066406,
//      'longitude' :	116.5925598144531,
//      'country' :	'中国',
//      'province' : '北京市',
//      'city' : '北京市',
//      'district' : '朝阳区',
//      'electricity' :	'100%',
//      'imsi' : '(null)(null)',
//      'impresstime' :	2147483647,
//      'ip' : '192.168.1.104',
//      'language' : 'zh',
//      'platform' : 2,
//      'version' :	'2.2.3',
//      'screen_width' : 320,
//      'screen_hight' : 568,
//      'brand' :	'苹果',
//      'devicename' : 'iPhone5,2',
//      'deviceid' : '32BCDE01-CB0F-4526-9E7E-AC175DE3F528',
//      'imei' : '32BCDE01-CB0F-4526-9E7E-AC175DE3F528',
//      'local' :	'GMT+8',
//      'lan' :	'wifi',
//      'wifi' : 'tanwenzhao',
//      'wifi_address' : '192.168.1.1',
//      'uid' :	1139667,
//      'operator' : 1,
//      'landscape' :	0,
//      'referer' : 'app_search?keyword=萝卜&type=1',
//      'page' : 'app_profile?userid=7613',
//      'launchidentifying' :	'C3C347D3-BDC5-4F37-A2D2-73273D13993A',
//      'switchidentifying' :	'2D016698-6B1F-4410-A2DE-6D7DDD380F55',
//      'pageidentifying' :	'9AB6B6F4-95C5-46A5-BAFC-4D14CDD87903'
//    };
//
//    Map<String, dynamic> postParams = {
//      "userid":"7613",
//      "devicemark":"62fd2fc6286fe5d9c5651b79a7e88756c7f33a5a7c9c8a3cb635cd479b571322",
//      "page":"2",
//      "orderid":"1",
//      "usertoken":"MmhOTkdFQU14TVRFTUZpTU1EemsyWmtOalltbXpOMWpZOHpaRGlXTmhNV1lXTkVNaE1XTkZGalpqVmpWbU5ZTVdOakFpTlRNbU0xTVQxR1kwemlPVEJNWWhZakZ6QTVNempEWmtOTk5EZ014M05EZGlNMlp6Z0RkaldNTTJZall4T1QyeE16TTJNV0ppVHhOMk1NeD0xTQ==",
//      "time":"2018-08-19 20:06:03",
//      "issmalltopic":"-1"
//    };
//
//    String getString = Transformer.urlEncodeMap(getParams);
//    String postJsonString = json.encode(postParams);
//
//    print(postJsonString);
//
//    Dio dio = Dio(options);
//    Response response = await dio.post("/sns/snslist/5/3?$getString", data: {'chk':'e50eae1233e775a59e063597c3552c6e', 'parameter':postJsonString});
//
      
      var resData = await httpRequest('sns/threadlist', {"page":"1","userid":"7613"});
      var result = resData['result'];

      result = json.decode(result);

      var code = result['code'];
      if (code == '1') {
        var sns = result['body']['sns'];
        LaosijiResults results = new LaosijiResults.fromJson(sns);
        print(results.results);
        return results;
      } else {
        print("error code:$code message:${result['message']}");
      }
  }

  httpRequest(String path, Map<String, dynamic> param) async {
    String expendedJson = json.encode(param);
    String strJson = parameterToJson(param, path, "AohhaT71wrlV4RRSlHNTyTk9noKZo4w_k0LhPJ7b-8DZ");
    String strUrl = "/$path/4/5/";

    String secUrl = "${options.baseUrl}$strUrl?apptoken=457AFE91B86EF9E35B19EFC204279D89&parameter=$strJson";

    Dio dio = Dio(options);
    Response response = await dio.post(assembleUrl(strUrl, expendedJson), data: {'chk':generateMd5(secUrl), 'parameter':strJson});

    print(response.data);
    return json.decode(response.data);
  }

  String parameterToJson(Map<String, dynamic> param, String path, String devicemark) {
    var now = DateTime.now();
    var formatter = DateFormat("yyyy-MM-dd HH:mm:ss");
    param["devicemark"] = devicemark;
    param["time"] = formatter.format(now);
    param["usertoken"] = "RGhEWXpaME1tTXp6WU5qTVlEekF5UU1OVjkwTWhaVGw9WmhObU1UTTBFMU5qVXpVMVpESURobFlaWnprelpUTW1SSVRqWVRsWlV6TTFaemd6MklNMllsMnpNalZZRTJZZ00ySTIyRFpUTlJObFltVVRVMVpNWkRJelI9WXpRbXo1WVRJWlV6TVV6Mkk9Wnp6VA==";
    return json.encode(param);
  }

  String assembleUrl(String url, String expendedJson) {
    Map<String, dynamic> getParams = {
      'production' : 'laosiji',
      'production_version' : Platform.isIOS ? 'ios2.9' : 'android2.9',
      'systemtag' : 4,
      'mac_address' : '02:00:00:00:00:00',
      'appid' : Platform.isIOS ? 'ios' : "android",
      'osversion' : Platform.isIOS ? '10.3.3' : '8.1.0',
      'chargestatus' :	1,
      'latitude' : 39.93043518066406,
      'longitude' :	116.5925598144531,
      'country' :	'中国',
      'province' : '北京市',
      'city' : '北京市',
      'district' : '朝阳区',
      'electricity' :	'84%',
      'imsi' : '(null)(null)',
      'android' : 'a54c2e4f4e66bc53',
      'local' :	'GMT+08:00',
      'lan' :	'wifi',
      'wifi' : 'TP_LINK',
      'wifi_address' : '192.168.1.1',
      'pvuid' : '',
      'uid' : '3762449',
      'mobile' : '',
      'operator' : 2,
      'landscape' :	0,
      'channel' : 'HuaWei',
      'winlog' : '',
      'referer' : 'app_search?keyword=萝卜报告&cityid=131',
      'page' : 'app_video_play_final?snsid=491929&resourceid=305580&resourcetype=0&cityid=131',
      'snsid' : '',
      'resourceid' : '',
      'resourcetype' : '',
      'adid' : '',
      'seat' : '',
      'startime' : '',
      'endtime' : '',
      'read_percent' : '',
      'video_percent' : '',
      'elapsed_time' : '',
      'interfaceurl' : '',
      'resultcode' : '',
      'errormsg' : '',
      'launchidentifying' :	'3c4eb9e0-b1ba-4c6b-9b24-8f2176bba6da',
      'switchidentifying' :	'34bc272d-6e83-4ab1-bd8c-c5072cb31c19',
      'pageidentifying' :	'afadc897-fa9f-4afc-8fc2-22e100108230',
      'baseexpanded' : expendedJson
    };

    String enString = Transformer.urlEncodeMap(getParams);
    return "$url?md5=${generateMd5(enString)}&$enString";
  }

  generateMd5(String data) {
    var content = new Utf8Encoder().convert(data);
    var digest = md5.convert(content);
    return digest.toString();
  }


}