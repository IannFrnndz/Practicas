import 'dart:convert';

import 'package:http/http.dart' as http;

import '../config/api_config.dart';
import '../models/oferta.dart';

class OfertaService {

  static Future<List<Oferta>> getByCategoria(String categoria) async {

    final response = await http.get(
      Uri.parse(
        '${ApiConfig.baseUrl}/ofertas/categoria/$categoria',
      ),
    );

    if (response.statusCode == 200) {

      final List<dynamic> data = jsonDecode(response.body);

      return data
          .map((json) => Oferta.fromJson(json))
          .toList();

    } else {

      throw Exception(
        'Error al cargar ofertas: ${response.statusCode}',
      );

    }
  }
}