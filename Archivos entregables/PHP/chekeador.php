<?php
require_once('conexion.php');

$idPaciente = 0;
$ph = 0;
$pco2 = 0;
$hco3 = 0;
$cl = 0;
$na = 0;

//STRING FINAL DE TODO EL DIAGNOSTICO

$diagfinal = 'el paciente sufre de ';



//SELECCIONAR ESTUDIO Y SEPARAR EN VARIBLES
$db = connect();
$statment=$db->prepare("SELECT * FROM estudios");
$statment->execute();
$results=$statment->fetchAll(PDO::FETCH_ASSOC);


foreach($results as $result) {
	$ph = $result['ph'];
	$pco2 = $result['pco2'];
	$hco3 = $result['hco3'];
	$cl = $result['cl'];
	$na = $result['na'];
	$idPaciente = $result['idPaciente'];

}

/////////////////////////////////////////////////

//LEVANTAR DIAGNOSTICO

$statment=$db->prepare("SELECT * FROM diagnosticos");
$statment->execute();
$results=$statment->fetchAll(PDO::FETCH_ASSOC);

foreach ($results as $resulta){
	if(($resulta['ph'] == 1 && $ph != 0 || $resulta['ph'] == 0) &&
		($resulta['pco2'] == 1 && $pco2 != 0 || $resulta['pco2'] == 0) &&
		($resulta['hco3'] == 1 && $hco3 != 0 || $resulta['hco3'] == 0) &&
		($resulta['cl'] == 1 && $cl != 0 || $resulta['cl'] == 0) &&
		($resulta['na'] == 1 && $na != 0 || $resulta['na'] == 0)){
		//var_dump(eval($resulta['condiciones']));
	var_dump($resulta['condiciones']);
		if(eval($resulta['condiciones'])){
			//necesitamos el dato y este es diferente de null o directamente ni lo necesitamos
			//TA TODO OK SE SIGUE
			$diagfinal = $diagfinal . $resulta['nombre'] . ', ';
		}else{
			//error: no se cumple condicion
		}
	}else{
		//error: no esta el dato pedido
	}
}

if (strlen($diagfinal) > 22){
	$db = connect();
    $stmt = $db->prepare("INSERT INTO diagnosticoscomp (diagFinal, idPaciente) 
    	VALUES (? , ? )");
    $stmt->execute([$diagfinal, $idPaciente]);
} else {
	$db = connect();
    $stmt = $db->prepare("INSERT INTO diagnosticoscomp (diagFinal, idPaciente) 
    	VALUES (? , ? )");
    $stmt->execute(['El paciente no sufre de nada', $idPaciente]);
}
$db = connect();
$stmt = $db->prepare("DELETE FROM estudios");
$stmt->execute();