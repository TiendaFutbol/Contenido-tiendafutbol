# ms-contenido

Java 17, Spring Boot 3.5.8, Oracle, JWT y Swagger. Puerto 8084.

## Ejecutar

1. Ejecutar el script de contenido del informe en Oracle (`C##ms_contenido` y sus cinco tablas).
2. Abrir esta carpeta (la que contiene `pom.xml`) en VS Code. Ejecutar `MsContenidoApplication.java` o `mvn spring-boot:run`.
3. Swagger: http://localhost:8084/swagger-ui.html
4. Para las rutas protegidas, iniciar sesión en identidad (8081), copiar el access token y usar Authorization > Bearer Token en Postman.

El archivo `application-local.yml` contiene la configuración local entregada y está ignorado por Git. No subir secretos al repositorio. No se usan FK entre microservicios. Las tablas y las FK internas deben existir según el script Oracle. Eliminar una categoría, blog o comentario referenciado devuelve 409; eliminar primero sus relaciones o registros dependientes.

## Endpoints

Base: http://localhost:8084/api

- `/categorias-blog`: GET público (lista y /{id}); POST, PUT /{id}, DELETE /{id} admin.
- `/blogs`: GET público (lista y /{id}); POST, PUT /{id}, DELETE /{id} admin.
- `/comentarios`: GET público (lista, /{id}, /blog/{blogId}); POST autenticado, PUT /{id} y DELETE /{id} dueño o admin.
- `/contactos`: POST público; GET (lista y /{id}), PUT /{id}, DELETE /{id} admin. Los mensajes de contacto no son públicos.

Los POST devuelven 201; los GET y PUT devuelven 200; los DELETE devuelven 204.

### Ejemplos Postman

POST /api/categorias-blog (admin):
```json
{"nombre":"Moda urbana"}
```
POST /api/blogs (admin; usar una categoría existente):
```json
{"titulo":"Moda urbana","resumen":"Ideas para vestir","contenido":"Combina prendas básicas.","img":"https://placehold.co/900x500","categoriaIds":[1]}
```
El autor y la fecha los asigna el backend. PUT de blog usa el mismo cuerpo.

POST /api/comentarios (cliente o admin; usar blog existente):
```json
{"blogId":1,"comentario":"Me gustó la publicación","comentarioPadreId":null}
```
Para responder otro comentario, enviar su ID como comentarioPadreId. PUT /api/comentarios/{id}:
```json
{"comentario":"Comentario editado"}
```
POST /api/contactos (público):
```json
{"nombre":"Ana Pérez","email":"ana@example.com","asunto":"Despacho","mensaje":"¿Cuánto demora el envío?"}
```
PUT de contacto utiliza el mismo cuerpo, con token admin.

La tabla blog_categoria se gestiona con @ManyToMany; no requiere un controlador independiente. Los IDs de usuario proceden del JWT. No se añadió OpenFeign ni se consulta identidad por cada petición: los cambios de rol pueden tardar hasta la expiración del access token en reflejarse aquí.

Pruebas sugeridas: lectura pública, CRUD admin, comentario cliente, edición de comentario ajeno (403), POST contacto público y GET contactos sin token (401). No se incluye target ni src/test. Se verificó estructura de archivos, no se compiló ni ejecutó contra Oracle.
