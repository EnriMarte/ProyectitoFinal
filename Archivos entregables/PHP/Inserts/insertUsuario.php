<?php
require_once('../conexion.php');

/*$nom = isset($_POST['nombre']) ? $_POST['nombre'] : '';
$hosp = isset($_POST['hospital']) ? $_POST['nombre'] : '';
$ape = isset($_POST['ape') ? $_POST['apellido']: '';
$usu = isset($_POST['usuario') ? $_POST['usuario']: '';
$contra = isset($_POST['contrasenia') ? $_POST['contrasenia']: '';
$uSesion = isset($_POST['ultimaSesion') ? $_POST['ultimaSesion']: '';
$matri = isset($_POST['matricula') ? $_POST['matricula']: '';
$permiso = isset($_POST['idCredencial') ? $_POST['idCredencial']: '';*/
$hosp = $_POST['hospital'];
$nombre = $_POST['nombre'];
$ape = $_POST['apellido'];
$usu = $_POST['usuario'];
$contra =  $_POST['contrasenia'];
$uSesion =  $_POST['ultimaSesion'];
$matri =  $_POST['matricula'];
$permiso =  $_POST['idCredencial'];



//$errores = [];
/*if($_POST)
{
	$errores = validarRegistracion($_POST);

	if(!count($errores))
	{
		//LLEVAR AL USUARIO A ......
		header('location: felicitaciones.php');
		exit;
	}
}
*/
if(isset($hosp, $nombre, $ape, $usu, $contra, $uSesion, $matri, $permiso)){

	$db = connect();
    $stmt = $db->prepare("INSERT INTO usuarios (hospital, nombre, apellido, usuario, contrasenia, ultimaSesion, matricula, idCredencial) 
    	VALUES (?, ? , ? , ? , ? , ? , ? , ?)");
        $stmt->execute([$hosp, $nombre, $ape,$usu,$contra,$uSesion, $matri, $permiso]);
    }

