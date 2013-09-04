<?php

include 'Classes/Conexao.php';

if (isset($_GET['usuario']) && isset($_GET['senha-antiga']) && isset($_GET['senha-nova'])) {
    $usuario = $_GET['usuario'];
    $senha_antiga = $_GET['senha-antiga'];
	$senha_nova = $_GET['senha-nova'];
    $conexao = new Conexao();
   
   	//Confere se o usuario esta correto
   	$predicado = "WHERE email = '".$usuario."' AND senha = '".$senha_antiga."'";
   	$resultado = $conexao->listar("usuarios", "*", $predicado);
   	
   	$ar = $resultado[0];
   	if(count($ar) == 0){
   		echo json_encode(array("status"=> false));
		return;
	}   

   	$query = "UPDATE usuarios SET senha = '".$senha_nova."' WHERE email = '".$usuario."';";
   	$resultado = $conexao->executaQuery($query);
   	if($resultado)
   		echo json_encode(array("status"=> true));
   	else
   		echo json_encode(array("status"=> false));
    
} else {
    echo 'Nao Existe';
}
?>

