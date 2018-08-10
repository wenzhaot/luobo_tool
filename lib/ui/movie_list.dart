import 'package:flutter/material.dart';
import 'package:luobo_tool/ui/movie_list_item.dart';
import 'package:luobo_tool/model/movie.dart';

class MovieList extends StatelessWidget {

  final List<Movie> _movies;

  MovieList(this._movies);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: _movies.length,
      itemBuilder: (context, i){
        return MovieListItem(_movies[i]);
      });
  }
}