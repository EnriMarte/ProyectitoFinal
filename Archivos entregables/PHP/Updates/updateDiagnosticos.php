<?php
require_once('../conexion.php');


$id = $_POST['idDiagnostico'];
$nom = $_POST['nombre'];
$condi = $_POST['condiciones'];
$phh = $_POST['ph'];
$pco22 = $_POST['pco2'];
$hco33 = $_POST['hco3'];
$cll = $_POST['cl'];
$naa = $_POST['na'];


if(isset($id,$nom,$condi,$phh,$pco22, $hco33, 
	$cll, $naa)){
 $db = connect();
    $stmt = $db->prepare("UPDATE diagnosticos SET 
    	nombre = :nom, 
    	condiciones = :condi,
    	ph = :phh,
    	pco2 = :pco22,
    	hco3 = :hco33,
    	cl = :cll,
    	na = :naa
    	WHERE idDiagnostico = :idu"); 
    $stmt->bindValue(":nom", $nom, PDO::PARAM_STR);       
    $stmt->bindValue(":condi", $condi, PDO::PARAM_STR);    
    $stmt->bindValue(":phh", $phh, PDO::PARAM_STR);
    $stmt->bindValue(":pco22", $pco22, PDO::PARAM_STR);       
    $stmt->bindValue(":hco33", $hco33, PDO::PARAM_STR);    
    $stmt->bindValue(":cll", $cll, PDO::PARAM_STR);
    $stmt->bindValue(":naa", $naa, PDO::PARAM_STR);
    $stmt->bindValue(":idu", $id, PDO::PARAM_INT);
 
    $stmt->execute();
}
