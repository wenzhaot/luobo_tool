import 'package:flutter/material.dart';
import 'package:luobo_tool/model/movie.dart';

class MovieListItem extends StatelessWidget {
  final Movie _movie;

  MovieListItem(this._movie);

  @override
  Widget build(BuildContext context) {
    return Card(
      child: InkWell(
        onTap: () => showActions(),
        child: Column(
          children: <Widget>[
            Hero(
              child: FadeInImage.assetNetwork(
                placeholder: "assets/placeholder.jpg",
                image: _movie.cover,
                fit: BoxFit.cover,
                width: double.infinity,
                height: 200.0,
                fadeInDuration: Duration(milliseconds: 50),
              ),
              tag: "Movie-Tag-${_movie.title}",
            ),
            Text(_movie.title),
          ],
        ),
      ),
    );

  }

  showActions() {

  }
}