<?php 

include 'Classes/Conexao.php';

	if(isset($_GET['id'])){
		$id = $_GET['id'];
		
		$con = new Conexao();
		
		$predicado = "WHERE id_bebe = $id";
		$resultado = $con->listar("progressos", "*", $predicado);
		
		if(!$resultado)
			echo json_encode(array("status" => false));
		else{
			
			$ar = $resultado[0];
			echo json_encode(array("status" => true, "progresso" => $ar['progresso']));
		}
	}else{
	
		echo json_encode(array("status" => false));
	}

?>
