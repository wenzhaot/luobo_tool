import 'package:flutter/material.dart';
import 'package:luobo_tool/ui/bmob_page.dart';

void main() {
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    home: Scaffold(
      appBar: AppBar(
        title: Text("Luobo Tool"),
      ),
      body: BmobPage(),
    ),
  ));
}