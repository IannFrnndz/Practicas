import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

class OfertaDetailScreen extends StatelessWidget {

  final Map<String, dynamic> oferta;

  const OfertaDetailScreen({super.key, required this.oferta});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(oferta["titulo"] ?? "Detalle"),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [

            // imagen
            oferta["imagenUrl"] != null
                ? Image.network(
              oferta["imagenUrl"],
              width: double.infinity,
              height: 250,
              fit: BoxFit.cover,
            )
                : Container(
              height: 250,
              color: Colors.grey,
              child: Center(child: Icon(Icons.image, size: 50)),
            ),

            SizedBox(height: 16),

            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [

                  // nombre de la oferta
                  Text(
                    oferta["titulo"] ?? "",
                    style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
                  ),

                  SizedBox(height: 10),

                  // precio
                  Text(
                    "💰 ${oferta["precio"]} €",
                    style: TextStyle(fontSize: 18, color: Colors.green),
                  ),

                  SizedBox(height: 10),

                  // categoria
                  Text(
                    "🌍 ${oferta["categoria"]}",
                    style: TextStyle(fontSize: 16),
                  ),

                  SizedBox(height: 20),

                  // descripcion de la oferta
                  Text(
                    oferta["descripcion"] ?? "Sin descripción",
                    style: TextStyle(fontSize: 16),
                  ),

                  SizedBox(height: 30),

                  // solicitud de mas informacion por el correo
                  SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      onPressed: () async {

                        final titulo = oferta["titulo"] ?? "";
                        final uri = Uri(
                          scheme: 'mailto',
                          path: 'viajessolparaiso@gmail.com',
                          query: Uri.encodeFull(
                              'subject=Consulta sobre oferta: $titulo&body=Hola, estoy interesado en la oferta "$titulo". Me gustaría recibir más información.'
                          ),
                        );

                        if (await canLaunchUrl(uri)) {
                          await launchUrl(uri);
                        } else {
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(content: Text("No se pudo abrir el correo")),
                          );
                        }
                      },
                      child: Text("Solicitar más información"),
                    )
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