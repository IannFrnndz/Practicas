import 'dart:convert';
import 'package:http/http.dart' as http;

class OfertaService {

  static const String baseUrl = "http://192.168.0.21:8085/api";

  static Future<List<dynamic>> getByCategoria(String categoria) async {
    final response = await http.get(
      Uri.parse('$baseUrl/ofertas/categoria/$categoria'),
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception("Error al cargar ofertas");
    }
  }
}