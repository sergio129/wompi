# Instrucciones para configurar Lombok en IntelliJ IDEA

## Problema
IntelliJ IDEA no reconoce las anotaciones de Lombok (@Builder, @Data, etc.) y marca errores en el código aunque compile correctamente.

## Solución paso a paso:

### 1. Instalar el plugin de Lombok en IntelliJ IDEA
1. Abre IntelliJ IDEA
2. Ve a **File → Settings** (o **Ctrl + Alt + S**)
3. Navega a **Plugins**
4. Busca "**Lombok**" en el Marketplace
5. Instala el plugin **Lombok** de Michail Plushnikov
6. Reinicia IntelliJ IDEA

### 2. Habilitar el procesamiento de anotaciones
1. Ve a **File → Settings** (o **Ctrl + Alt + S**)
2. Navega a **Build, Execution, Deployment → Compiler → Annotation Processors**
3. **Marca** la casilla: **"Enable annotation processing"**
4. Asegúrate de que esté seleccionado: **"Obtain processors from project classpath"**
5. Haz clic en **Apply** y **OK**

### 3. Invalidar caché de IntelliJ IDEA
1. Ve a **File → Invalidate Caches...**
2. Marca todas las opciones:
   - Clear file system cache and Local History
   - Clear downloaded shared indexes
   - Clear VCS Log caches and indexes
3. Haz clic en **Invalidate and Restart**

### 4. Rebuild del proyecto
1. Después del reinicio, ve a **Build → Rebuild Project**
2. Espera a que termine la compilación

### 5. Verificación
Después de estos pasos, IntelliJ debería reconocer:
- `@Builder` y el método `.builder()`
- `@Data` (getters/setters automáticos)
- Todos los métodos generados por Lombok

## Cambios realizados en el proyecto:

✅ **Java actualizado de 8 a 11 LTS** - Elimina las advertencias de versión obsoleta
✅ **Lombok 1.18.34** - Versión estable y compatible con Java 11
✅ **Configuración de annotation processors** - Ya está en build.gradle

## Si el problema persiste:

Ejecuta estos comandos en la terminal:
```bash
cd E:\Proyectos\Wompi
.\gradlew clean build --refresh-dependencies
```

Esto forzará la descarga de las dependencias y la regeneración del proyecto.

