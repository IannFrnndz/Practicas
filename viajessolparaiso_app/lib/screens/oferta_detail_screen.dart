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

    final subject = Uri.encodeComponent('Consulta oferta ${oferta.titulo}');
    final body = Uri.encodeComponent('Hola, estoy interesado en la oferta ${oferta.titulo}');

    final uri = Uri.parse(
      'mailto:reservas@viajessolparaiso.com?subject=$subject&body=$body',
    );

    try {

      await launchUrl(
        uri,
        mode: LaunchMode.externalApplication,
      );

    } catch (e) {

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Error al abrir correo: $e'),
        ),
      );
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
        title: Text(
          oferta.titulo,
          style: const TextStyle(
            color: Colors.black87,
            fontWeight: FontWeight.w600,
          ),
        ),
      ),

      body: SingleChildScrollView(

        child: Column(

          crossAxisAlignment: CrossAxisAlignment.start,

          children: [

            // IMAGEN GRANDE
            if (oferta.imagenUrl.isNotEmpty)
              ClipRRect(
                borderRadius: const BorderRadius.vertical(
                  bottom: Radius.circular(24),
                ),
                child: Image.network(
                  oferta.imagenUrl,
                  width: double.infinity,
                  height: 280,
                  fit: BoxFit.cover,
                ),
              ),

            // CONTENIDO PRINCIPAL
            Padding(

              padding: const EdgeInsets.all(16),

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

                child: Padding(
                  padding: const EdgeInsets.all(20),

                  child: Column(

                    crossAxisAlignment: CrossAxisAlignment.start,

                    children: [

                      //  TITULO
                      Text(
                        oferta.titulo,
                        style: const TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                          color: Colors.black87,
                        ),
                      ),

                      const SizedBox(height: 16),

                      //  PRECIO DESTACADO
                      Container(
                        padding: const EdgeInsets.symmetric(
                          horizontal: 16,
                          vertical: 10,
                        ),
                        decoration: BoxDecoration(
                          color: Colors.orange.shade50,
                          borderRadius: BorderRadius.circular(14),
                        ),
                        child: Text(
                          '${oferta.precio} €',
                          style: TextStyle(
                            fontSize: 22,
                            fontWeight: FontWeight.bold,
                            color: Colors.orange.shade800,
                          ),
                        ),
                      ),

                      const SizedBox(height: 20),

                      //  DESCRIPCIÓN
                      Text(
                        oferta.descripcion,
                        style: TextStyle(
                          fontSize: 16,
                          color: Colors.grey.shade700,
                          height: 1.5,
                        ),
                      ),

                      const SizedBox(height: 30),

                      //  BOTÓN MODERNO
                      SizedBox(

                        width: double.infinity,

                        child: ElevatedButton(

                          onPressed: () => enviarCorreo(context),

                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.orange,
                            padding: const EdgeInsets.symmetric(
                              vertical: 16,
                            ),
                            shape: RoundedRectangleBorder(
                              borderRadius:
                              BorderRadius.circular(16),
                            ),
                            elevation: 0,
                          ),

                          child: const Text(
                            'Solicitar información',
                            style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      )
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}