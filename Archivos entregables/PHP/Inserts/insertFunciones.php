<?php

require_once('../conexion.php');

$nombre = $_POST['nombre'];
$condicion = $_POST['condiciones'];
$funcion = $_POST['funcion'];




if(isset($nombre,$condicion,$funcion)){
	$db = connect();
    $stmt = $db->prepare("INSERT INTO funciones (nombre, funcion, condiciones) 
    	VALUES (? , ? , ? )");
        $stmt->execute([$nombre, $funcion, $condicion]);
}