import 'package:flutter/material.dart';
import 'package:luobo_tool/ui/movie_list.dart';
import 'package:luobo_tool/api/laosiji_api.dart';
import 'package:luobo_tool/model/movie.dart';

class LaosijiPage extends StatefulWidget {

  @override
  LaosijiPageState createState() => LaosijiPageState();

}


class LaosijiPageState extends State<LaosijiPage> {

  List<Movie> mMovies = [];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    getAllMovies();
  }

  getAllMovies() async {
    LaosijiApi().getMovies();
//    BmobResults results = await BmobApi().getMovies();
//    setState(() {
//      mMovies = results.results;
//    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text("Laosiji"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(10.0),
        child: MovieList(mMovies),
      ),
    );
  }
}