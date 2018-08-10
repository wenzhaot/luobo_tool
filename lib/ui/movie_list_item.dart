import 'package:flutter/material.dart';
import 'package:luobo_tool/model/movie.dart';

class MovieListItem extends StatelessWidget {
  final Movie _movie;

  MovieListItem(this._movie);

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text(_movie.title),
    );
  }
}