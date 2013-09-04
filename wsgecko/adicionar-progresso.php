<?php

include 'Classes/Conexao.php';

if (isset($_GET['id_bebe']) && isset($_GET['progresso'])) {
	$id = $_GET['id_bebe'];
	$progresso = $_GET['progresso'];
	
    $conexao = new Conexao();
	$conexao->abreConexao();
	$con = $conexao->getConexao();   

		if (@pg_query($con, "BEGIN")) {
   		
			$query = "INSERT INTO progresso  VALUES('$id', '$progresso');";	
			if (@pg_query($con, $query)) {
				echo json_encode(array("status" => true);
			} else {
				@pg_query($con, "ROLLBACK");
				echo  json_encode(array("status" => false, "msg" => "Error commit"));
			}
			
		} else {
			echo  json_encode(array("status" => false, "msg" => "Sequencia bebe"));
		}
} else {
    echo  json_encode(array("status" => false, "msg" => "Não Existe"));
}
?>
