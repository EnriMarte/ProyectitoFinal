<?php
require_once('../conexion.php');


$id = $_POST['idPacientes'];
$nom = $_POST['nombre'];
$ape = $_POST['apellido'];
$sexo = $_POST['sexo'];
$altura = $_POST['altura'];
$edad = $_POST['edad'];
$peso = $_POST['peso'];
$tipoSangre = $_POST['tipoSangre'];
$medicoCabecera = $_POST['medicoCabecera'];
$ultimoEstudio = $_POST['ultimoEstudio'];


/*$id = 6;
$nom = 'nombre';
$ape = 'apellido';
$sexo = 'sexo';
$altura = 'altura';
$edad = 'edad';
$peso = 'peso';
$tipoSangre = 'tipoSangre';
$medicoCabecera = 'medicoCabecera';
$ultimoEstudio = 'ultimoEstudio';*/


if(isset($id,$nom,$ape,$sexo,$altura,$edad,$peso, $tipoSangre, 
	$medicoCabecera, $ultimoEstudio)){
 $db = connect();
    $stmt = $db->prepare("UPDATE pacientes SET 
    	nombre = :nom, 
    	apellido = :ape,
    	sexo = :sexo,
    	altura = :altura,
    	edad = :edad,
    	peso = :peso,
    	tipoSangre = :tipoSangre,
    	medicoCabecera = :medicoCabecera,
    	ultimoEstudio = :ultimo 
    	WHERE idPacientes = :idu"); 
    $stmt->bindValue(":nom", $nom, PDO::PARAM_STR);       
    $stmt->bindValue(":ape", $ape, PDO::PARAM_STR);    
    $stmt->bindValue(":sexo", $sexo, PDO::PARAM_STR);
    $stmt->bindValue(":altura", $altura, PDO::PARAM_STR);       
    $stmt->bindValue(":edad", $edad, PDO::PARAM_STR);    
    $stmt->bindValue(":peso", $peso, PDO::PARAM_STR);
    $stmt->bindValue(":tipoSangre", $tipoSangre, PDO::PARAM_STR);       
    $stmt->bindValue(":medicoCabecera", $medicoCabecera, PDO::PARAM_STR);    
    $stmt->bindValue(":ultimo", $ultimoEstudio, PDO::PARAM_STR);
    $stmt->bindValue(":idu", $id, PDO::PARAM_INT);
 
    $stmt->execute();
}
