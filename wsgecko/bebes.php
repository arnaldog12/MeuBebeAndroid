<?php

include 'Classes/Conexao.php';

if (isset($_GET['usuario']) && isset($_GET['senha'])) {
    $usuario = $_GET['usuario'];
	$senha = $_GET['senha'];
    $conexao = new Conexao();
   
	$tabela = "usuarios";
	$predicado = "WHERE email = '".$usuario."' AND senha = '".$senha."'";
	$resposta = $conexao->listar($tabela, "*", $predicado);
	if(!$resposta){
		
		echo json_encode(array("status" => false));
		return;
	}

	$colunas = "id_bebe";
   	$tabela = "responsaveis";
   	$predicado = "WHERE responsavel = '".$usuario."'";
   	$id_bebes = $conexao->listar($tabela, $colunas, $predicado);
   	
   	$arg0 = $id_bebes[0];
   	if(count($arg0)==0){
   		echo json_encode(array("status" => true, "n_bebes" => 0));
		return;
	}
   	
	$tamanho = count($id_bebes);
	$retorno = array("status"=>true, "n_bebes" => $tamanho);
   	for($i = 0; $i < $tamanho; $i++){
   		
   		$array = $id_bebes[$i];
		$id_atual = $array["id_bebe"];

		$colunas = "*";
	   	$tabela = "bebes";
   		$predicado = "WHERE id = ".$id_atual;
	   	$bebe = $conexao->listar($tabela, $colunas, $predicado);   		
	   	
		$array = $bebe[0];
	   	$retorno[] = $array;
   	}
   	
   	echo json_encode($retorno);
    
} else {
    echo 'Nao Existe';
}
?>

