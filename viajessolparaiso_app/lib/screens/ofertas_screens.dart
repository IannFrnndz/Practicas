import 'package:flutter/material.dart';

import '../models/oferta.dart';
import '../services/oferta_service.dart';
import 'oferta_detail_screen.dart';

class OfertasScreen extends StatefulWidget {

  final String categoria;

  const OfertasScreen({
    super.key,
    required this.categoria,
  });

  @override
  State<OfertasScreen> createState() => _OfertasScreenState();
}

class _OfertasScreenState extends State<OfertasScreen> {

  late Future<List<Oferta>> ofertasFuture;

  @override
  void initState() {
    super.initState();

    ofertasFuture =
        OfertaService.getByCategoria(widget.categoria);
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(

      backgroundColor: const Color(0xFFF5F7FA),

      appBar: AppBar(
        elevation: 0,
        backgroundColor: Colors.white,
        centerTitle: true,
        title: Text(
          widget.categoria.replaceAll("_", " "),
          style: const TextStyle(
            color: Colors.black87,
            fontWeight: FontWeight.w600,
          ),
        ),
      ),

      body: FutureBuilder<List<Oferta>>(

        future: ofertasFuture,

        builder: (context, snapshot) {

          if (snapshot.connectionState ==
              ConnectionState.waiting) {

            return const Center(
              child: CircularProgressIndicator(),
            );
          }

          if (snapshot.hasError) {

            return Center(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: Text(
                  'Error al cargar ofertas.\n${snapshot.error}',
                  textAlign: TextAlign.center,
                ),
              ),
            );
          }

          final ofertas = snapshot.data ?? [];

          if (ofertas.isEmpty) {

            return const Center(
              child: Text(
                'No hay ofertas disponibles',
              ),
            );
          }

          return ListView.builder(

            padding: const EdgeInsets.all(14),
            itemCount: ofertas.length,

            itemBuilder: (context, index) {

              final oferta = ofertas[index];

              return GestureDetector(

                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (_) =>
                          OfertaDetailScreen(
                            oferta: oferta,
                          ),
                    ),
                  );
                },

                child: Container(

                  margin: const EdgeInsets.only(bottom: 18),

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
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [

                      // IMAGEN
                      ClipRRect(
                        borderRadius: const BorderRadius.vertical(
                          top: Radius.circular(24),
                        ),
                        child: oferta.imagenUrl.isNotEmpty
                            ? Image.network(
                          oferta.imagenUrl,
                          height: 230,
                          width: double.infinity,
                          fit: BoxFit.cover,
                        )
                            : Container(
                          height: 230,
                          color: Colors.grey.shade300,
                          child: const Center(
                            child: Icon(Icons.image, size: 50),
                          ),
                        ),
                      ),

                      // CONTENIDO
                      Padding(
                        padding: const EdgeInsets.all(18),
                        child: Column(
                          crossAxisAlignment:
                          CrossAxisAlignment.start,
                          children: [

                            // TITULO
                            Text(
                              oferta.titulo,
                              style: const TextStyle(
                                fontSize: 20,
                                fontWeight: FontWeight.bold,
                                color: Colors.black87,
                              ),
                            ),

                            const SizedBox(height: 12),

                            //  PRECIO DESTACADO
                            Container(
                              padding: const EdgeInsets.symmetric(
                                horizontal: 14,
                                vertical: 8,
                              ),
                              decoration: BoxDecoration(
                                color: Colors.orange.shade50,
                                borderRadius:
                                BorderRadius.circular(14),
                              ),
                              child: Text(
                                '${oferta.precio} €',
                                style: TextStyle(
                                  color: Colors.orange.shade800,
                                  fontSize: 20,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            ),

                            const SizedBox(height: 12),

                            // CATEGORIA
                            Row(
                              children: [
                                Icon(
                                  Icons.place,
                                  color: Colors.orange.shade600,
                                  size: 18,
                                ),
                                const SizedBox(width: 6),
                                Text(
                                  oferta.categoria,
                                  style: TextStyle(
                                    color: Colors.orange.shade700,
                                    fontWeight: FontWeight.w600,
                                  ),
                                ),
                              ],
                            ),

                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}