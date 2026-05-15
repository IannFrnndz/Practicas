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

      appBar: AppBar(
        title: Text(widget.categoria),
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

            itemCount: ofertas.length,

            itemBuilder: (context, index) {

              final oferta = ofertas[index];

              return Card(

                margin: const EdgeInsets.all(10),

                child: ListTile(

                  leading: oferta.imagenUrl.isNotEmpty
                      ? Image.network(
                    oferta.imagenUrl,
                    width: 70,
                    fit: BoxFit.cover,
                  )
                      : const Icon(Icons.image),

                  title: Text(oferta.titulo),

                  subtitle: Column(
                    crossAxisAlignment:
                    CrossAxisAlignment.start,
                    children: [

                      const SizedBox(height: 5),

                      Text(
                        '${oferta.precio} €',
                      ),

                      Text(
                        oferta.categoria,
                      ),
                    ],
                  ),

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
                ),
              );
            },
          );
        },
      ),
    );
  }
}