<?php

include 'Classes/Conexao.php';

if (isset($_GET['usuario']) && isset($_GET['senha'])) {
    $usuario = $_GET['usuario'];
	$senha = $_GET['senha'];
    $conexao = new Conexao();
   
   	$query = "INSERT INTO usuarios VALUES('".$usuario."', '".$senha."');";
   	$resultado = $conexao->executaQuery($query);
	$retorno = array("status" => $resultado);
   	echo json_encode($retorno);
    
} else {
    echo 'Nao Existe';
}
?>

