import pytesseract
from PIL import Image, ImageEnhance, ImageFilter
import re
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

# -------------------------
# Función para preprocesar imagen
# -------------------------
def preprocess_image(path):
    img = Image.open(path)
    img = img.convert('L')  # blanco y negro
    img = img.filter(ImageFilter.SHARPEN)
    enhancer = ImageEnhance.Contrast(img)
    img = enhancer.enhance(2)
    return img

# -------------------------
# Función para extraer y clasificar
# -------------------------
def extract_offer(path):
    img = preprocess_image(path)
    
    
    
    # --- DETECTAR TÍTULO ---
    # Extraer datos de cada palabra
    data = pytesseract.image_to_data(img, lang='spa', output_type=pytesseract.Output.DICT)

    # Inicializamos variables
    max_height = 0
    titulo = ""

    # Recorremos cada palabra
    for i, word in enumerate(data['text']):
        if word.strip() and not any(char.isdigit() for char in word):
            altura = data['height'][i]
            if altura > max_height:
                max_height = altura
                titulo = word

    
        # --- DETECTAR PRECIO ---
    precio = None
    for i, line in enumerate(data['text']):
        line_clean = line.strip()
        if line_clean:
            # Buscar palabra "precio" o símbolo "€"
            if 'precio' in line_clean.lower() or '€' in line_clean:
                # Extraer número usando regex (número entero o decimal)
                match = re.search(r'(\d{1,6}(?:[.,]\d{1,2})?)', line_clean)
                if match:
                    precio_str = match.group(1).replace(',', '.')
                    precio = float(precio_str)
                    break  # tomar solo el primer precio que encuentre
        
    # --- DETECTAR DESCRIPCIÓN ---
    descripcion = ""
    for i, line in enumerate(data['text']):
        line_clean = line.strip()
        if line_clean and line_clean not in titulo.split() and (precio is None or str(precio) not in line_clean):
            descripcion += line_clean + " "
    
    return {
        "titulo": titulo,
        "precio": precio,
        "descripcion": descripcion.strip()
    }

# -------------------------
# PROBAR CON UNA IMAGEN
# -------------------------
ruta = "ejemplo4.jpg"
resultado = extract_offer(ruta)
print(resultado)