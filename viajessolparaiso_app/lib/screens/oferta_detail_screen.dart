import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

import '../models/oferta.dart';

class OfertaDetailScreen extends StatelessWidget {

  final Oferta oferta;

  const OfertaDetailScreen({
    super.key,
    required this.oferta,
  });

  Future<void> enviarCorreo(BuildContext context) async {

    final uri = Uri(
      scheme: 'mailto',
      path: 'reservas@viajessolparaiso.com',
      query:
      'subject=Consulta oferta ${oferta.titulo}',
    );

    // control de errores con mensajes personalizados
    try {

      if (await canLaunchUrl(uri)) {

        await launchUrl(uri);

      } else {

        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text(
              'No hay aplicación de correo instalada',
            ),
          ),
        );
      }

    } catch (e) {

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            'Error al abrir correo: $e',
          ),
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(

      appBar: AppBar(
        title: Text(oferta.titulo),
      ),

      body: SingleChildScrollView(

        child: Column(

          crossAxisAlignment:
          CrossAxisAlignment.start,

          children: [

            if (oferta.imagenUrl.isNotEmpty)

              Image.network(
                oferta.imagenUrl,
                width: double.infinity,
                height: 250,
                fit: BoxFit.cover,
              ),

            Padding(

              padding: const EdgeInsets.all(16),

              child: Column(

                crossAxisAlignment:
                CrossAxisAlignment.start,

                children: [

                  Text(
                    oferta.titulo,
                    style: const TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                    ),
                  ),

                  const SizedBox(height: 15),

                  Text(
                    '${oferta.precio} €',
                    style: const TextStyle(
                      fontSize: 20,
                    ),
                  ),

                  const SizedBox(height: 20),

                  Text(
                    oferta.descripcion,
                    style: const TextStyle(
                      fontSize: 16,
                    ),
                  ),

                  const SizedBox(height: 30),

                  SizedBox(

                    width: double.infinity,

                    child: ElevatedButton(

                      onPressed: () => enviarCorreo(context),

                      child: const Text(
                        'Solicitar información',
                      ),
                    ),
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}