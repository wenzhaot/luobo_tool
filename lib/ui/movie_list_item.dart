import 'package:flutter/material.dart';
import 'package:luobo_tool/model/movie.dart';
import 'package:flutter/services.dart';
import 'package:luobo_tool/api/bmob_api.dart';
import 'package:dialog_loading/dialog_loading.dart';

class MovieListItem extends StatelessWidget {
  final Movie _movie;
  BuildContext mContext;
  MovieListItem(this._movie);

  String mLaosijiUrl;

  @override
  Widget build(BuildContext context) {
    mContext = context;
    return Card(
      child: InkWell(
        onTap: () {
          showActions(context);
        },
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

  showActions(BuildContext context) {
    print("Clicked: ${_movie.url}");
    if (_movie.url == 'bmob') {
      showDialog(
          context: context,
          builder: (BuildContext mContext) {
            return AlertDialog(
              title: Text('Bmob'),
              content: TextField(
                decoration: new InputDecoration(
                    labelText: "Laosiji url"
                ),
                onChanged: (String text) {
                  mLaosijiUrl = text;
                },
              ),
              actions: <Widget>[
                FlatButton(
                  child: Text('Update'),
                  onPressed: () {
                    startUpdate(context);
                  },
                )
              ],
            );
          }
      );
    } else if (_movie.url != "") {
      Clipboard.setData(ClipboardData(text: _movie.url));
      showDialog(context: context, builder: (BuildContext mContext) { return AlertDialog(title: Text('Copied!')); });
    }

  }

  startUpdate(BuildContext context) async {
    Navigator.pop(context);
    DialogLoading(context).showLoading();
    await BmobApi().updateLaosijiUrl(mLaosijiUrl, _movie.mId);
    DialogLoading(context).hideLoading();
  }

}