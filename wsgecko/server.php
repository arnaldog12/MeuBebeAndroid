<?php

include 'Classes/Conexao.php';
$dados = lerDados();
escreveDados($dados);
$ip=$_SERVER['REMOTE_ADDR'];
ipVisitante($dados,$ip);
if (isset($_GET['data'])) {
    $data = $_GET['data'];
  //  echo $data;    
    $conexao = new Conexao();
   
    $retorno = $conexao->listar("dicas_usuarios", "nome,dica,categoria", "WHERE data > '" . $data . "'");
//    echo "depois conexao";
      	 $array2 = $retorno[0];
    
     if(count($array2)==0){
        // echo json_encode();
        return;
     }

	$arg = array();
  //  echo "coutnt: ".count($retorno);
    
    for ($i = 0; $i < count($retorno); $i++) {
        $arg = $retorno[$i];
    //    echo $arg['email'] . " ";
     //   echo $arg['nome'] . " ";
     //   echo $arg['categoria'] . " <br>";
    }
    
    $saida2 = $conexao->listar('dicas_usuarios', "MAX(data) as maior_Data", " ");
    //echo $i;
    //echo $retorno[$i];
    $retorno[$i] = $saida2[0];
   // echo "oi";
    echo json_encode($retorno);
  //  echo "<br>E: ".$retorno[$i]['max']."<br>";
    /* 
   for($i=0; $i < count($retorno)-1; $i++){
       $arg=$retorno[$i];
        echo $arg['email'] . " ";
        echo $arg['nome'] . " ";
        echo $arg['categoria'] . " <br>";
   }*/
   
    //echo "<br>E: ".$retorno[$i]['max']."<br>";
   
    
} else {
    echo 'Nao Existe';
}
//echo $hora;
/*    if ($data == '01/21/2013') {
  $me = array(
  'nome' => 'Arnaldo',
  'email' => 'arnaldo.g12@gmail.com',
  'dica' => 'Ao completar 6 meses, introduzir de forma lenta e gradual outros alimentos, mantendo o leite materno ate os dois anos de idade ou mais.',
  'categoria' => 'Alimentação',
  // 'maior_data' => '20/01/2013'
  );
  echo json_encode($me);
  }else
  echo 'Hello, World!'; */

function lerDados(){
$arquivo = fopen('leitura.txt','r');
if ($arquivo == false) die('Não foi possível abrir o arquivo.');
$linha = fgets($arquivo);	
//echo $linha;
//fwrite($arquivo, $linha+1);
fclose($arquivo);
return $linha;
}

function escreveDados($dados){
$dados = $dados + 1;
$arquivo = fopen('leitura.txt','w');
fwrite($arquivo, $dados);
fclose($arquivo);

}

function ipVisitante($dados,$ip){
//$date = getdate();
//$data = $date[mday]."/".$date[mon]."/".$date[year]." ".$date[hours].":".$date[minutes].":".$date[seconds];
$dados = $dados + 1;
$arquivo = fopen('ip.txt', 'a+');
fwrite($arquivo, $dados." - ".$ip."\n");
fclose($arquivo);
}
?>
