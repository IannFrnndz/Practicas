import 'package:flutter/material.dart';
import 'ofertas_screen.dart';

class HomeScreen extends StatelessWidget {
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
        title: Text("Ofertas Viajes Solparaiso"),
      ),
      body: GridView.builder(
        padding: EdgeInsets.all(10),
        gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 2,
          childAspectRatio: 1.2,
          crossAxisSpacing: 10,
          mainAxisSpacing: 10,
        ),
        itemCount: categorias.length,
        itemBuilder: (context, index) {
          final categoria = categorias[index];

          return GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (_) => OfertasScreen(categoria: categoria),
                ),
              );
            },
            child: Card(
              color: Colors.blueAccent,
              child: Center(
                child: Text(
                  categoria,
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 20,
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