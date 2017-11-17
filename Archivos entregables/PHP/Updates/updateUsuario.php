<?php
require_once('../conexion.php');


$id = $_POST['idUsuario'];
$nom = $_POST['nombre'];
$hosp = $_POST['hospital'];
$ape = $_POST['apellido'];
$usu = $_POST['usuario'];
$contra = $_POST['contrasenia'];
$uSesion = $_POST['ultimaSesion'];
$matri = $_POST['matricula'];
$permiso = $_POST['idCredencial'];



if(isset($id,$nom,$ape,$hosp,$usu,$contra,$uSesion, $matri, $permiso)){
 $db = connect();
    $stmt = $db->prepare("UPDATE usuarios SET 
    	nombre = :nom, 
    	hospital = :hos,
    	apellido = :ape,
    	usuario = :usu,
    	contrasenia = :con,
    	ultimaSesion = :uses,
    	matricula = :matri,
    	idCredencial = :perm WHERE idUsuario = :idu"); 
    $stmt->bindValue(":nom", $nom, PDO::PARAM_STR);       
    $stmt->bindValue(":hos", $hosp, PDO::PARAM_STR);    
    $stmt->bindValue(":ape", $ape, PDO::PARAM_STR);
    $stmt->bindValue(":usu", $usu, PDO::PARAM_STR);       
    $stmt->bindValue(":con", $contra, PDO::PARAM_STR);    
    $stmt->bindValue(":uses", $uSesion, PDO::PARAM_STR);
    $stmt->bindValue(":matri", $matri, PDO::PARAM_STR);       
    $stmt->bindValue(":perm", $permiso, PDO::PARAM_STR);    
    $stmt->bindValue(":idu", $id, PDO::PARAM_INT);
 
    $stmt->execute();
}
