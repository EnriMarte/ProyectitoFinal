<?php

require_once('../conexion.php');
$nombre = $_POST['nombre'];
$condi = $_POST['condiciones'];
$ph = $_POST['ph'];
$pco2 = $_POST['pco2'];
$hco3 = $_POST['hco3'];
$cl = $_POST['cl'];
$na = $_POST['na'];


if(isset($nombre,$condi, $ph, $pco2, $hco3, $cl, $na)){
	$db = connect();
    $stmt = $db->prepare("INSERT INTO diagnosticos (nombre, condiciones, ph, pco2, hco3, cl, na) 
    	VALUES (? , ? , ? , ? , ? , ? , ? )");
        $stmt->execute([$nombre, $condi, $ph, $pco2, $hco3, $cl, $na]);
}