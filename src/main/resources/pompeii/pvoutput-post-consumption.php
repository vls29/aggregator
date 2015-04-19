<?php

$url = 'http://localhost:28080/aggregator/services/data/store/v1_0';
//$url2 = 'http://192.168.0.100:28080/aggregator/services/data/store/v1_0';

$importImpulses = $_POST["importImpulses"];
$generationImpulses = $_POST["generationImpulses"];
$msBetweenCalls = $_POST["msBetweenCalls"];
$importMultiplier = $_POST["importMultiplier"];
$generationMultiplier = $_POST["generationMultiplier"];
$mainsVoltage = $_POST["mainsVoltage"];

$myvars = 'importImpulses=' . $importImpulses . '&generationImpulses=' . $generationImpulses . '&msBetweenCalls=' . $msBetweenCalls . '&importMultiplier=' . $importMultiplier . '&generationMultiplier=' . $generationMultiplier . '&mainsVoltage=' . $mainsVoltage;

$ch = curl_init( $url );
curl_setopt( $ch, CURLOPT_POST, 1);
curl_setopt( $ch, CURLOPT_POSTFIELDS, $myvars);
curl_setopt( $ch, CURLOPT_FOLLOWLOCATION, 1);
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt( $ch, CURLOPT_HTTPHEADER, array('X-Data-Source: METER'));
curl_exec( $ch );

/*$ch2 = curl_init( $url2 );
curl_setopt( $ch2, CURLOPT_POST, 1);
curl_setopt( $ch2, CURLOPT_POSTFIELDS, $myvars);
curl_setopt( $ch2, CURLOPT_FOLLOWLOCATION, 1);
curl_setopt( $ch2, CURLOPT_RETURNTRANSFER, 1);
curl_setopt( $ch2, CURLOPT_HTTPHEADER, array('X-Data-Source: METER'));
curl_exec( $ch2 );*/
?>
