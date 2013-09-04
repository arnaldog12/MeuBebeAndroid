<?php

include 'Classes/Conexao.php';
include 'adiciona-progresso.php';

if (isset($_GET['usuario']) && isset($_GET['nome']) && isset($_GET['idade']) && isset($_GET['sexo']) && isset($_GET['progresso'])) {
	$usuario = $_GET['usuario'];
	$nome = $_GET['nome'];
	$idade = $_GET['idade'];
	$sexo = $_GET['sexo'];
	$progresso = $_GET['progresso'];

	$con_bd = new Conexao();
	$con_bd->abreConexao();
	$con = $con_bd->getConexao();

	if ($bid = @pg_fetch_assoc(@pg_query($con, "SELECT nextval('bebes_id_seq') as bid;"))) {
	
		if (@pg_query($con, "BEGIN")) {
   		
			$query = "INSERT INTO bebes (id, nome, idade, sexo) VALUES('$bid[bid]', '$nome', '$idade', '$sexo');";	
			if (@pg_query($con, $query)) {

				$query_resp = "INSERT INTO responsaveis(responsavel, id_bebe) VALUES('$usuario', '$bid[bid]');";
				if (@pg_query($con, $query_resp)) {

					$query = "INSERT INTO progressos VALUES('$bid[bid]', '$progresso');";	
					if (@pg_query($con, $query)) {	

						if (@pg_query($con, "COMMIT")) {
	
							echo json_encode(array("status" => true, "bebe_id" => $bid["bid"]));
						} else {

							@pg_query($con, "ROLLBACK");
							echo  json_encode(array("status" => false, "msg" => "Error commit"));
						}
					} else {

						@pg_query($con, "ROLLBACK");	
						echo  json_encode(array("status" => false, "msg" => "Insert progresso"));
					}
				} else {

					@pg_query($con, "ROLLBACK");	
					echo  json_encode(array("status" => false, "msg" => "Insert responsaveis"));	
				}
			} else {

				@pg_query($con, "ROLLBACK");
				echo  json_encode(array("status" => false, "msg" => "Error insert bebe"));	
			}	
		} else {

			echo  json_encode(array("status" => false, "msg" => "Erro begin"));	
		}
	} else {
	
		echo  json_encode(array("status" => false, "msg" => "Sequencia bebe"));	
	}		
} else {

    echo  json_encode(array("status" => false, "msg" => "Não Existe"));
}
?>

