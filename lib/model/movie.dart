
class Movie {
  final String title;
  final String cover;
  final String url;
  final String mId;

  const Movie({this.title, this.cover, this.url, this.mId});

  factory Movie.fromJson(Map<String, dynamic> parsedJson){
    return Movie(
        title: parsedJson['title'],
        cover: parsedJson['cover'],
        mId: parsedJson['objectId'],
        url: 'bmob'
    );
  }

  factory Movie.fromLaosijiJson(Map<String, dynamic> parsedJson){
    var list = parsedJson['list'];
    var url = '';
    if (list.length > 0) {
      url = list.first['videourl']['hdinfo']['url'];
    }

    return Movie(
        title: parsedJson['title'],
        cover: parsedJson['image']['url'],
        url : url
    );
  }

}