<?php

include 'Classes/Conexao.php';

if (isset($_GET['id']) && 
	isset($_GET['nome']) && 
	isset($_GET['idade']) && 
	isset($_GET['sexo'])) {
		
	$id = $_GET['id'];
	$nome = $_GET['nome'];
	$idade = $_GET['idade'];
	$sexo = ($_GET['sexo']) ? 'TRUE': 'FALSE';

    	$conexao = new Conexao();
	
	$predicado = "WHERE id = $id";
	$retorno = $conexao->listar("bebes", "*", $predicado);
	
	if(!$retorno){
		echo json_encode(array("status" => false));
		return;
	}

   	$query = "UPDATE bebes SET nome = '$nome', idade = '$idade', sexo = $sexo  WHERE id = $id;";
   	$resultado = $conexao->executaQuery($query);
   	if($resultado)
   		echo json_encode(array("status"=> true));
   	else
   		echo json_encode(array("status"=> false));
    
} else {
    echo 'Nao Existe';
}
?>
