import 'package:flutter/material.dart';
import '../services/oferta_service.dart';
import 'oferta_detail_screen.dart';

class OfertasScreen extends StatefulWidget {
  final String categoria;

  const OfertasScreen({super.key, required this.categoria});

  @override
  State<OfertasScreen> createState() => _OfertasScreenState();
}

class _OfertasScreenState extends State<OfertasScreen> {

  late Future<List<dynamic>> ofertas;

  @override
  void initState() {
    super.initState();

    ofertas = OfertaService.getByCategoria(widget.categoria);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Ofertas de ${widget.categoria}"),
      ),
      body: FutureBuilder<List<dynamic>>(
        future: ofertas,
        builder: (context, snapshot) {

          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text("Error al cargar datos"));
          }

          final data = snapshot.data!;

          return ListView.builder(
            itemCount: data.length,
            itemBuilder: (context, index) {

              final oferta = data[index];

              return Card(
                margin: EdgeInsets.all(10),
                child: ListTile(

                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => OfertaDetailScreen(oferta: oferta),
                      ),
                    );
                  },

                  leading: oferta["imagenUrl"] != null
                      ? Image.network(
                    oferta["imagenUrl"],
                    width: 60,
                    fit: BoxFit.cover,
                  )
                      : Icon(Icons.image),

                  title: Text(oferta["titulo"] ?? "Sin título"),

                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(oferta["descripcion"] ?? ""),
                      SizedBox(height: 5),
                      Text("💰 ${oferta["precio"]} €"),
                      Text("🌍 ${oferta["categoria"]}"),
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