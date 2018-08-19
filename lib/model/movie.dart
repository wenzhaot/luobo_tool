
class Movie {
  final String title;
  final String cover;

  const Movie({this.title, this.cover});

  factory Movie.fromJson(Map<String, dynamic> parsedJson){
    return Movie(
        title: parsedJson['title'],
        cover : parsedJson['cover']
    );
  }

}