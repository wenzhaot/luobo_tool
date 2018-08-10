import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:json_annotation/json_annotation.dart';


class BmobApi {

  final Options options = Options(
    baseUrl: 'https://api.bmob.cn/1/classes',
    headers: {'X-Bmob-Application-Id':'ebff9fa4d9118ec545fc89873a6e7f04','X-Bmob-REST-API-Key':'b845b388616e80424565f98843fc98f6'}
  );

  test() async {
    var where = {'appId':'junwu'};
    Dio dio = Dio(options);
    Response response = await dio.get('/engine', data: {'where':json.encode(where)});
    print(response.data);
  }

}