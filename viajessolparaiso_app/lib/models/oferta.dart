class Oferta {

  final int id;
  final String titulo;
  final String descripcion;
  final double precio;
  final String imagenUrl;
  final String categoria;

  Oferta({
    required this.id,
    required this.titulo,
    required this.descripcion,
    required this.precio,
    required this.imagenUrl,
    required this.categoria,
  });

  factory Oferta.fromJson(Map<String, dynamic> json) {
    return Oferta(
      id: json['id'],
      titulo: json['titulo'] ?? '',
      descripcion: json['descripcion'] ?? '',
      precio: (json['precio'] ?? 0).toDouble(),
      imagenUrl: json['imagenUrl'] ?? '',
      categoria: json['categoria'] ?? '',
    );
  }
}