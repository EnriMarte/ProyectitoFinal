<?php

require_once('../conexion.php');
$ph = $_POST['ph'];
$pco2 = $_POST['pco2'];
$hco3 = $_POST['hco3'];
$cl = $_POST['cl'];
$na = $_POST['na'];
$idPaciente = $_POST['idPaciente'];


if(isset($ph, $pco2, $hco3, $cl, $na, $idPaciente)){
	$db = connect();
    $stmt = $db->prepare("INSERT INTO estudios (ph, pco2, hco3, cl, na, idPaciente) 
    	VALUES (? , ? , ? , ? , ? , ? )");
        $stmt->execute([$ph, $pco2, $hco3, $cl, $na, $idPaciente]);
}