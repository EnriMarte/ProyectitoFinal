<?php

require_once('../conexion.php');

$db = connect();
$statement=$db->prepare("SELECT * FROM funciones");
$statement->execute();
$results=$statement->fetchAll(PDO::FETCH_ASSOC);
$json=json_encode($results);

echo $json;
