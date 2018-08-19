import 'package:flutter/material.dart';
import 'package:luobo_tool/ui/movie_list.dart';
import 'package:luobo_tool/api/bmob_api.dart';
import 'package:luobo_tool/model/movie.dart';
import 'package:luobo_tool/ui/laosiji_page.dart';

class BmobPage extends StatefulWidget {

  @override
  BmobPageState createState() => BmobPageState();

}


class BmobPageState extends State<BmobPage> {

  List<Movie> mMovies = [];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    getAllMovies();
  }

  getAllMovies() async {
    BmobResults results = await BmobApi().getMovies();
    setState(() {
      mMovies = results.results;
    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text("Luobo Tool"),
        actions: <Widget>[
          IconButton(
              icon: const Icon(Icons.settings),
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (context) => LaosijiPage()));
              })
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(10.0),
        child: MovieList(mMovies),
      ),
    );
  }
}