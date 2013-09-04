<?php

include 'Classes/Conexao.php';

if (isset($_GET['data'])) {
    $data = $_GET['data'];
    $conexao = new Conexao();
   
    $retorno = $conexao->listar("dicas_usuarios", "nome,dica,categoria", "WHERE data > '" . $data . "'");
      	 $array2 = $retorno[0];
    
     if(count($array2)==0){
        return;
     }

    $saida2 = $conexao->listar('dicas_usuarios', "MAX(data) as maior_Data", " ");
	$resultado = $saida2[0];
    
    for ($i = 0; $i < count($retorno); $i++) {
        $resultado[] = $retorno[$i];
    }

    echo json_encode($resultado);
    
} else {
    echo 'Nao Existe';
}
?>

