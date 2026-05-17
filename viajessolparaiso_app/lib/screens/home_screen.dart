import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../models/oferta.dart';
import '../config/api_config.dart';
import 'ofertas_screens.dart';
import 'oferta_detail_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {

  List<Oferta> ofertasUltimaHora = [];
  bool isLoading = true;
  String? error;

  final List<String> categorias = [
    "DISNEY",
    "FESTIVOS",
    "ESPAÑA",
    "EUROPA",
    "AMERICA",
    "CARIBE",
    "AFRICA",
    "ASIA",
    "OCEANIA",
    "EXCURSIONES",
    "ULTIMA_HORA",
    "COMUNIDAD_DE_MADRID",
    "CRUCEROS"
  ];

  @override
  void initState() {
    super.initState();
    cargarOfertas();
  }

  Future<void> cargarOfertas() async {
    try {

      final response = await http.get(
        Uri.parse('${ApiConfig.baseUrl}/ofertas/categoria/ULTIMA_HORA'),
      );

      if (response.statusCode == 200) {

        final List data = json.decode(response.body);

        setState(() {
          ofertasUltimaHora =
              data.map((e) => Oferta.fromJson(e)).toList();
          isLoading = false;
        });

      } else {
        throw Exception('Error en API');
      }

    } catch (e) {
      setState(() {
        error = e.toString();
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      backgroundColor: const Color(0xFFF5F7FA),

      appBar: AppBar(
        elevation: 0,
        backgroundColor: Colors.white,
        centerTitle: true,
        title: const Text(
          "Viajes SolParaíso",
          style: TextStyle(
            color: Colors.black87,
            fontWeight: FontWeight.w600,
          ),
        ),
      ),

      body: CustomScrollView(
        slivers: [

          // TÍTULO
          SliverToBoxAdapter(
            child: Padding(
              padding: const EdgeInsets.fromLTRB(16, 16, 16, 8),
              child: Text(
                "OFERTAS DE ÚLTIMA HORA",
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                  color: Colors.grey.shade800,
                ),
              ),
            ),
          ),

          //  CARRUSEL
          SliverToBoxAdapter(
            child: SizedBox(
              height: 230,
              child: _buildCarrusel(),
            ),
          ),

          //  GRID
          SliverPadding(
            padding: const EdgeInsets.all(14),
            sliver: SliverGrid(
              delegate: SliverChildBuilderDelegate(
                    (context, index) {

                  final categoria = categorias[index];

                  return GestureDetector(
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (_) => OfertasScreen(
                            categoria: categoria,
                          ),
                        ),
                      );
                    },
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(24),
                        boxShadow: [
                          BoxShadow(
                            color: Colors.black.withOpacity(0.06),
                            blurRadius: 12,
                            offset: const Offset(0, 6),
                          ),
                        ],
                      ),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [

                          // ICONO / BANDERA
                          Text(
                            _getEmoji(categoria),
                            style: const TextStyle(fontSize: 36),
                          ),

                          const SizedBox(height: 10),

                          // TEXTO
                          Padding(
                            padding: const EdgeInsets.symmetric(horizontal: 8),
                            child: Text(
                              categoria.replaceAll("_", " "),
                              textAlign: TextAlign.center,
                              style: const TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.w600,
                                color: Colors.black87,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  );
                },
                childCount: categorias.length,
              ),
              gridDelegate:
              const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2,
                crossAxisSpacing: 12,
                mainAxisSpacing: 12,
                childAspectRatio: 1.1,
              ),
            ),
          ),
        ],
      ),
    );
  }

  //  EMOJIS
  String _getEmoji(String categoria) {
    switch (categoria) {
      case "ESPAÑA":
        return "🇪🇸";
      case "EUROPA":
        return "🇪🇺";
      case "AMERICA":
        return "🌎";
      case "CARIBE":
        return "🏝️";
      case "AFRICA":
        return "🌍";
      case "ASIA":
        return "🌏";
      case "OCEANIA":
        return "🌊";
      case "DISNEY":
        return "🏰";
      case "CRUCEROS":
        return "🚢";
      case "EXCURSIONES":
        return "🥾";
      case "ULTIMA_HORA":
        return "🔥";
      case "COMUNIDAD_DE_MADRID":
        return "🏙️";
      case "FESTIVOS":
        return "🎉";
      default:
        return "✈️";
    }
  }

  //  WIDGET CARRUSEL
  Widget _buildCarrusel() {

    if (isLoading) {
      return const Center(child: CircularProgressIndicator());
    }

    if (error != null) {
      return Center(
        child: Text("Error: $error"),
      );
    }

    if (ofertasUltimaHora.isEmpty) {
      return const Center(
        child: Text("No hay ofertas de última hora"),
      );
    }

    return PageView.builder(

      controller: PageController(viewportFraction: 0.88),

      itemCount: ofertasUltimaHora.length,

      itemBuilder: (context, index) {

        final oferta = ofertasUltimaHora[index];

        return GestureDetector(

          onTap: () {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (_) =>
                    OfertaDetailScreen(oferta: oferta),
              ),
            );
          },

          child: Container(

            margin: const EdgeInsets.symmetric(horizontal: 8, vertical: 10),

            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(24),
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withOpacity(0.06),
                  blurRadius: 12,
                  offset: const Offset(0, 6),
                ),
              ],
            ),

            child: ClipRRect(
              borderRadius: BorderRadius.circular(24),
              child: Stack(
                fit: StackFit.expand,
                children: [

                  Image.network(
                    oferta.imagenUrl,
                    fit: BoxFit.cover,
                    errorBuilder: (_, __, ___) =>
                    const Center(child: Icon(Icons.image)),
                  ),

                  Container(
                    decoration: BoxDecoration(
                      gradient: LinearGradient(
                        colors: [
                          Colors.transparent,
                          Colors.black.withOpacity(0.7)
                        ],
                        begin: Alignment.topCenter,
                        end: Alignment.bottomCenter,
                      ),
                    ),
                  ),

                  Positioned(
                    bottom: 14,
                    left: 14,
                    right: 14,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [

                        Text(
                          oferta.titulo,
                          style: const TextStyle(
                            color: Colors.white,
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),

                        const SizedBox(height: 4),

                        Text(
                          '${oferta.precio} €',
                          style: const TextStyle(
                            color: Colors.orangeAccent,
                            fontSize: 16,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}