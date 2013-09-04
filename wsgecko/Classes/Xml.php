<?php
include 'Conexao.php';

Class xml {

    function __construct() {
        $this->conexao = new Conexao();
    }

    function addDica($document, $nome, $email, $dica, $categoria) {
        #criar contato
        $contato = $document->createElement("dica");

        #criar nó nome
        $nomeElm = $document->createElement("nome", $nome);

        #email
        $emailElm = $document->createElement("email", $email);

        #dica
        $dicaElm = $document->createElement("dica", $dica);

        #categoria
        $cateElm = $document->createElement("categoria", $categoria);

        $contato->appendChild($nomeElm);
        $contato->appendChild($emailElm);
        $contato->appendChild($dicaElm);
        $contato->appendChild($cateElm);
        
        //$conexao = new Conexao();
        //$sql = "INSERT INTO teste VALUES('".$nome."', '".$email."', '".$dica."', '".$categoria."', 'data'); ";
        //echo $sql;
        //$conexao->executaQuery($sql);
        
        
        return $contato;
    }

    function lerXML($nomeXML) {
        @header('Content-Type: text/html; charset=utf-8');
#carrega o arquivo XML e retornando um Array
        $xml = simplexml_load_file($nomeXML);

        foreach ($xml->dica as $dica) {
            echo $dica->nome . "<br>";
            echo $dica->email . "<br>";
            echo $dica->dica . "<br>";
            echo $dica->categoria . "<br>";
            echo "<br>" . "<br>";
        }
    }
    
    function printXML($xml){
        $dom = new DOMDocument();
        $dom->loadXML($xml);
        //header("Content-Type: text/xml");
        //print $xml->l;
    }

}

?>
