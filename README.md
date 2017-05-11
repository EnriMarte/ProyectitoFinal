# Proyecto Final
Diego Pertierra, Enrico Martella, Ian Rossi

### Resumen teórico:
Los tejidos que constituyen al organismo están compuestos entre otras cosas por células. La célula, es la unidad viviente que cumple con todas las funcionas biológicas en los organismos complejos. Para que cada célula en el organismo cumpla la función para la que fue diseñada genéticamente, necesita estar rodeada por un sistema denominado: MEDIO INTERNO.
Este medio, permite la vida y posee una gran importancia biomédica, en virtud de que si este falla, el equilibrio homeostático se ve en riesgo y en ocasiones el fallo puede ser incompatible con la vida.
Este trabajo consiste en analizar, diagnosticar, tratar y pronosticar las alteraciones que se puedan producir en los elementos de este medio interno. Los valores a analizar son: Ph, PO2 (presión de oxígeno), PCHO3 (presión de bicarbonato), PCO2 (presión de dióxido de carbono), Na (sodio), Cl (cloro), K (potasio).

### Misión:
Lograr crear un software que permita análisis rápidos y precisos que simplifiquen el proceso de evaluación de los resultados de laboratorio, logrando dar un probable diagnóstico e identificando enfermedades en pacientes, internados en áreas críticas: quirófano, unidad de cuidados intensivos, unidad coronaria, enfermos renales, infecciosos graves y salas de tratamiento de urgencias.
### Visión:
Lograr hacer que clínicas logren implementar exitosamente el software en su uso rutinario.

### Funciones:
<b>Laboratorio:</b>
<ul>
<li>Carga de datos:</li>
  <ul>
  <li>Manual.</li>
  <li>Detección OCR.</li>
  <li>Recepción de paquete de datos (en el caso de que la máquina tenga capacidad Bluetooth o Wifi).</li>
  </ul>
<li>Manejo de datos:</li>
  <ul>
  <li>Validación de datos (que estén dentro de los valores aceptables/críticos).</li>
  <li>Elección de funciones a usar y procesamiento de datos.</li>
  </ul>
<li>Presentación de datos:</li>
  <ul>
  <li>Resultado de funciones y diagnóstico primario (<i>¿exacto?</i>).</li>
  </ul>
</ul>

<b>ABM pacientes:</b>
  •	Agregar, modificar y eliminar pacientes. 😊
  •	Datos a guardar: 
    	Nombre.
    	Apellido.
    	Imagen (opcional).
    	Sexo.
    	Altura.
    	Edad.
    	Peso.
    	Tipo de sangre.
    	Médico de cabecera.
    	Fecha de último estudio.
    	Ver los estudios del paciente.
    	Gráfico evolutivo en el caso de que se le haya realizado al paciente más de dos estudios en las últimas 48 horas.
    
<b>ABM usuarios:</b>
  •	Agregar, modificar y eliminar usuarios. 😊
  •	Datos a guardar:
    	Nombre.
    	Apellido.
    	Usuario.
    	Contraseña.
    	Última sesión.
    	Matrícula.
    	Hospital/es.
    	Área/s.
    	Credencial (cantidad de permisos. <i>Ejemplo: 0 = super usuario, 1 = jefe de área</i>).
    	<i>in confirmar – inicio de sesión extra (detección de huella dactilar o reconocimiento facial).</i>

<b>ABM funciones:</b>
  •	Agregar, modificar y eliminar funciones. 😊
  •	Datos a guardar:
    	Nombre (alias) de la función.
    	Función.

<b>ABM credenciales:</b>
  •	Agregar, modificar y eliminar credenciales. 😊
  •	Nombre de la credencial.
  •	A qué partes de la aplicación tiene acceso.
<i>Los usuarios de credencial nivel n no podrán modificar ni eliminar a los usuarios de credencial n-1. El usuario de credencial 0 es el super usuario y puede modificar y eliminar al resto.</i>



<ul>
  <li>Sistema de credenciales con posibilidad de ingreso anónimo restringido</li>
  <li>Base de datos de pacientes</li>
  <li>Analizar datos y dar un diagnóstico primario en base a las alteraciones: Estado de situación y variables</li>
  <li>Posibles procedimientos (en base al punto anterior)</li>
  <li>Posibilidad de calificar el procedimiento dado</li>
  <li>Procedimientos pasados del paciente</li>
  <li>Posibilidad de mandar a imprmir el resultado del programa.</li>
  <li>Análisis gráfico del mejoramiento o desmejoramiento del paciente (en base a estudios anteriores realizados dentro de las 24 horas)</li>
</ul>
