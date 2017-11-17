<?php
require_once('../conexion.php');

$nom = $_POST['nombre'];
$apellido = $_POST['apellido'];
$sexo = $_POST['sexo'];
$altura =  $_POST['altura'];
$edad = $_POST['edad'];
$peso =  $_POST['peso'];
$tiposangre =  $_POST['tipoSangre'];
$medicoc =  $_POST['medicoCabecera'];
$ultimoe =  $_POST['ultimoEstudio'];

//$ultimoestudio =  $_POST['idCredencial'];
if(isset($nom, $apellido, $edad, $sexo, $peso, $altura, $tiposangre, $medicoc, $ultimoe)){
$db = connect();
    $stmt = $db->prepare("INSERT INTO pacientes (nombre, apellido, sexo, altura, edad, peso, tipoSangre, medicoCabecera, ultimoEstudio) 
    	VALUES (?, ? , ? , ? , ? , ? , ? , ? , ?)");
        $stmt->execute([$nom, $apellido, $sexo,$altura,$edad,$peso, $tiposangre, $medicoc, $ultimoe]);



} 