# Administración de envíos

Aplicación de escritorio Java Swing para registrar, listar y retirar envíos terrestres, aéreos y marítimos.

## Requisitos

- Java 17 o superior.

## Ejecución

Desde la raíz del proyecto:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp out App
```

## Pruebas

Desde la raíz del proyecto, compile y ejecute las pruebas del servicio:

```powershell
javac -encoding UTF-8 -cp out -d out (Get-ChildItem -Recurse test -Filter *.java | ForEach-Object { $_.FullName })
java -cp out tests.ServicioEnviosTest
```

La tarifa se calcula como `distancia × tarifa por km + peso × recargo por kg`:

| Tipo | Tarifa por km | Recargo por kg |
| --- | ---: | ---: |
| Terrestre | $1.500 | $5.000 |
| Aéreo | $2.000 | $800 |
| Marítimo | $4.000 | $1.000 |

El peso y la distancia deben ser mayores que cero. Se aceptan decimales con punto o coma (por ejemplo, `2.5` o `2,5`); no se deben ingresar separadores de miles. Los códigos se consideran únicos sin distinguir mayúsculas y minúsculas.

## Organización

- `modelos`: clase abstracta `Envio` y sus tipos concretos, que calculan la tarifa mediante polimorfismo.
- `fabricas`: Factory Method abstracto y una fábrica concreta para cada medio.
- `servicios`: interfaz y servicio para agregar, listar y retirar envíos.
- `controladores`: coordinación entre la vista y el servicio.
- `vistas`: interfaz gráfica Swing.

Para agregar otro medio (por ejemplo, Express), cree un subtipo de `Envio` que implemente `getTipo()` y `calcularTarifa()`, cree su fábrica concreta extendiendo `FabricaEnvio` y registre la fábrica en `App`. No es necesario modificar los tipos o fábricas existentes ni la lógica del servicio, controlador o vista.
