Documentación del Proyecto: TFM Integración Continua

1. Introducción
Este proyecto consiste en la implementación de un flujo de integración y despliegue continuo (CI/CD) para una aplicación de gestión de fichas de películas, dividida en un microservicio de datos y una interfaz de usuario.

2. Cambios en el Microservicio (moviecards-service)
Se ha actualizado la entidad Actor para incluir el campo deadDate (fecha de fallecimiento).

Se ha desplegado el servicio en Azure para servir estos datos a la aplicación principal.

3. Actualización de la Aplicación Principal (moviecards)
Para integrar la nueva funcionalidad, se realizaron las siguientes modificaciones:

Modelo: Se añadió el atributo deadDate con anotaciones de formato @DateTimeFormat(pattern = "yyyy-MM-dd").

Interfaz (UI):

Se actualizó form.html para permitir la entrada de la fecha de fallecimiento.

Se modificó list.html para mostrar la nueva columna en el listado de actores.

Controladores: Se refactorizó el código en ActorController y MovieController para eliminar literales duplicados y constructores redundantes, cumpliendo con las reglas de SonarQube.

4. Estrategia de Pruebas
Se ha garantizado la estabilidad mediante tres niveles de testing:

Pruebas Unitarias: Verificación de lógica de servicios y modelos usando Mockito.

Pruebas de Integración: Validación de la persistencia y el contexto de Spring.

Pruebas End-to-End (E2E): Uso de Selenium para simular la navegación del usuario. Se ajustaron los índices de las tablas para dar cabida a la nueva columna de "Fecha Fallecimiento".

5. Pipeline de CI/CD (GitHub Actions)
El flujo automatizado incluye:

Build: Compilación con Java 11.

Test: Ejecución de todas las pruebas.

QA (SonarCloud): Análisis de calidad. Se configuró un Quality Gate que bloquea el despliegue si existen 5 o más problemas críticos. Para este paso, se utilizó Java 17 debido a requisitos del scanner de Sonar.

Stage: Despliegue automático en el entorno de pre-producción al hacer merge en la rama master.

Deploy: Despliegue final tras aprobación manual en GitHub Actions.