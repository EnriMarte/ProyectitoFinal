<?php
require_once('../conexion.php');

$id = $_POST['id'];
if(isset($id)){
	$db = connect();
    $stmt = $db->prepare("DELETE FROM usuarios WHERE idUsuario = :id");
    $stmt->bindValue(':id', $id);
    $stmt->execute();
}