<?php

require_once('conexion.php');

$db = connect();
$statement=$db->prepare("SELECT * FROM diagnosticoscomp ORDER BY idDiagnostico DESC LIMIT 1");
$statement->execute();
$results=$statement->fetchAll(PDO::FETCH_ASSOC);
$json=json_encode($results);

echo $json;
