<?php

$url = 'http://localhost:28080/aggregator/services/data/store/v1_0';
//$url2 = 'http://192.168.0.100:28080/aggregator/services/data/store/v1_0';

$generationCumulative = $_POST["v1"];
$geneartionPower = $_POST["v2"];
$myvars = 'v1=' . $generationCumulative . '&v2=' . $geneartionPower;

$ch = curl_init( $url );
curl_setopt( $ch, CURLOPT_POST, 1);
curl_setopt( $ch, CURLOPT_POSTFIELDS, $myvars);
curl_setopt( $ch, CURLOPT_FOLLOWLOCATION, 1);
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt( $ch, CURLOPT_HTTPHEADER, array('X-Data-Source: GENERATION'));
curl_exec( $ch );

/*$ch2 = curl_init( $url2 );
curl_setopt( $ch2, CURLOPT_POST, 1);
curl_setopt( $ch2, CURLOPT_POSTFIELDS, $myvars);
curl_setopt( $ch2, CURLOPT_FOLLOWLOCATION, 1);
curl_setopt( $ch2, CURLOPT_RETURNTRANSFER, 1);
curl_setopt( $ch2, CURLOPT_HTTPHEADER, array('X-Data-Source: GENERATION'));
curl_exec( $ch2 );*/
?>
