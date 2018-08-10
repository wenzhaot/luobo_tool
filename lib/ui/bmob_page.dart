import 'package:flutter/material.dart';
import 'package:luobo_tool/model/movie.dart';
import 'package:luobo_tool/ui/movie_list.dart';
import 'package:luobo_tool/api/bmob_api.dart';

class BmobPage extends StatelessWidget {

  _buildMovies() {
    return <Movie>[
      const Movie(
        title: '111',
        cover: '111'
      ),
      const Movie(
          title: '222',
          cover: '222'
      )
    ];
  }

  @override
  Widget build(BuildContext context) {
    BmobApi().test();
    return Scaffold(body: MovieList(_buildMovies()));
  }

}