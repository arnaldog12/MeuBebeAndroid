<?php

include 'Classes/Conexao.php';

if (isset($_GET['id']) && 
	isset($_GET['progresso'])) {
		
	$id = $_GET['id'];
	$progresso = $_GET['progresso'];

    $conexao = new Conexao();
	
	$predicado = "WHERE id_bebe = $id";
	$retorno = $conexao->listar("progressos", "*", $predicado);
	
	if(!$retorno){
		echo json_encode(array("status" => false));
		return;
	}

   	$query = "UPDATE progressos SET progresso = '$progresso' WHERE id_bebe = $id;";
   	$resultado = $conexao->executaQuery($query);
   	if($resultado)
   		echo json_encode(array("status"=> true));
   	else
   		echo json_encode(array("status"=> false));
    
} else {
    echo 'Nao Existe';
}
?>
