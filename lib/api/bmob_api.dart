import 'package:dio/dio.dart';
import 'package:luobo_tool/model/movie.dart';

class BmobResults {
  final List<Movie> results;

  const BmobResults({this.results});

  factory BmobResults.fromJson(Map<String, dynamic> parsedJson){
    var resultsFromJson  = parsedJson['results'];
    List<Movie> movieList = [];
    for (var movieItem in resultsFromJson){
      movieList.add(new Movie.fromJson(movieItem));
    }

    return BmobResults(
        results: movieList
    );
  }
}


class BmobApi {

  final Options options = Options(
    baseUrl: 'https://api.bmob.cn/1/classes',
    headers: {'X-Bmob-Application-Id':'a98677c8acbae6bb1e769d8f06150850','X-Bmob-REST-API-Key':'32e5b9ded9f241bded2b6a6e15dc05ac'}
  );

  test() async {
    getMovies();
  }

  getMovies() async {
    Dio dio = Dio(options);
    Response response = await dio.get('/video', data: {'order':'-createdAt','limit':'10'});
    BmobResults results = new BmobResults.fromJson(response.data);
    print(response.data);
    return results;
  }

  updateLaosijiUrl(String url, String objectId) async {
    Dio dio = Dio(options);
    Response response = await dio.put("/video/$objectId", data: {'laosijiLink':url,'visible':true});

    print(response.data);
  }

}