<?php

function adicionaBebe($usuario, $nome, $idade, $sexo){
	//Pegar proximo id para bebes
	if ($bid = @pg_fetch_assoc(@pg_query($con, "SELECT nextval('bebes_id_seq') as bid;"))) {
	
		if (@pg_query($con, "BEGIN")) {
   		
			$query = "INSERT INTO bebes (id, nome, idade, sexo) VALUES('$bid[bid]', '$nome', '$idade', '$sexo');";	
			if (@pg_query($con, $query)) {

				$query_resp = "INSERT INTO responsaveis(responsavel, id_bebe) VALUES('$usuario', '$bid[bid]');";
				if (@pg_query($con, $query_resp)) {

					if (@pg_query($con, "COMMIT")) {

						$id = $bid["bid"];
						//echo json_encode(array("status" => true, "bebe_id" => $bid["bid"]));
					} else {

						@pg_query($con, "ROLLBACK");
						echo  json_encode(array("status" => false, "msg" => "Error commit"));
						return;
					}
				} else {

					@pg_query($con, "ROLLBACK");	
					echo  json_encode(array("status" => false, "msg" => "Insert responsaveis"));
					return;
				}
			} else {

				@pg_query($con, "ROLLBACK");
				echo  json_encode(array("status" => false, "msg" => "Error insert bebe"));
				return;
			}	
		} else {

			echo  json_encode(array("status" => false, "msg" => "Erro begin"));
			return;
		}
	} else {

		echo  json_encode(array("status" => false, "msg" => "Sequencia bebe"));
		return;
	}	
}

?>
