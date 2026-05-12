Trabajo Práctico N°2 - Programación III
=======================================

Este proyecto es una aplicación Java desarrollada para la materia Programación III de la Universidad Nancional de General Sarmiento.

📋 Requisitos Previos
---------------------

Para ejecutar este proyecto, asegúrate de tener instalado:

-   **Java JDK:** Versión 11 o superior.

-   **IDE:** Eclipse IDE (Recomendado) o VS Code con el Pack de Extensiones de Java.

Configuración Inicial (Cómo importar)
----------------------------------------

Para que el proyecto funcione correctamente y reconozca las librerías, sigue estos pasos:

1.  **Clonar el repositorio:**

    ```
    git clone https://github.com/HoffmannPedro/MTS-Kruskal-TP2PIII.git
    ```

2.  **Importar en Eclipse:**

    -   Abre Eclipse.

    -   Ve a `File` -> `Import...`.

    -   Selecciona `General` -> `Existing Projects into Workspace`.

    -   En `Select root directory`, busca la carpeta donde clonaste el proyecto.

    -   Asegúrate de que el proyecto esté marcado y dale a `Finish`.

> [!IMPORTANT]  
> **Librerías Externas:** Todas las dependencias necesarias están en la carpeta `/lib`. El archivo `.classpath` ya está configurado para tomarlas de ahí. Si ves errores de compilación, haz clic derecho en el proyecto -> `Build Path` -> `Configure Build Path` y verifica que los archivos en `Libraries` apunten correctamente a la carpeta `/lib`.

🛠️ Flujo de Trabajo en Git (Reglas)
-------------------------------------------

Para evitar conflictos y mantener el repositorio limpio, se recomienda seguir estas reglas:

1.  **Antes de empezar a programar:** Siempre haz un `git pull` para traer los últimos cambios.

2.  **Uso de Ramas:** Trabajaremos directamente sobre la rama `main` para este TP. **No crees ramas nuevas.**

3.  **Archivos de sistema:** El archivo `.gitignore` ya está configurado para no subir basura. No modifiques las carpetas `.settings` ni subas archivos de la carpeta `bin/`.

4.  **Commits:** Hace commits pequeños con mensajes claros (ej: "Agregada lógica de validación en Clase Localidad").

📁 Estructura del Proyecto
--------------------------

-   `/src`: Contiene todo el código fuente Java.

-   `/lib`: Librerías externas (`.jar`). **No borrar ni mover.**

-   `/test`: Pruebas unitarias.

-   `localidades`: Archivo de datos que guarda las localidades ingresadas en el sistema, de no existir se autogenera al ingresar la primera localidad.

* * * * *

**Integrantes:**

-   [Pedro Hoffmann]

-   [Valentina Arri Mareschi]

-   [Agustín Casco]