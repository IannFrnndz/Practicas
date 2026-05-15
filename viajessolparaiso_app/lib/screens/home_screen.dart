import 'package:flutter/material.dart';
import 'ofertas_screens.dart';

class HomeScreen extends StatelessWidget {

  HomeScreen({super.key});

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
  Widget build(BuildContext context) {

    return Scaffold(

      appBar: AppBar(
        title: const Text("Viajes Solparaíso"),
      ),

      body: GridView.builder(

        padding: const EdgeInsets.all(12),

        itemCount: categorias.length,

        gridDelegate:
        const SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 2,
          crossAxisSpacing: 10,
          mainAxisSpacing: 10,
          childAspectRatio: 1.2,
        ),

        itemBuilder: (context, index) {

          final categoria = categorias[index];

          return Card(

            elevation: 3,

            child: InkWell(

              borderRadius: BorderRadius.circular(12),

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

              child: Center(
                child: Text(
                  categoria,
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}