<?php
require_once('../conexion.php');


$id = $_POST['idFunciones'];
$nom = $_POST['nombre'];
$funcion = $_POST['funcion'];
$condiciones = $_POST['condiciones'];


if(isset($id,$nom,$funcion,$condiciones)){
 $db = connect();
    $stmt = $db->prepare("UPDATE funciones SET 
    	nombre = :nom, 
    	funcion = :funcion,
    	condiciones = :condicion
    	WHERE idFunciones = :idu"); 
    $stmt->bindValue(":nom", $nom, PDO::PARAM_STR);       
    $stmt->bindValue(":funcion", $funcion, PDO::PARAM_STR);    
    $stmt->bindValue(":condicion", $condiciones, PDO::PARAM_STR);
    $stmt->bindValue(":idu", $id, PDO::PARAM_INT);
 
    $stmt->execute();
}