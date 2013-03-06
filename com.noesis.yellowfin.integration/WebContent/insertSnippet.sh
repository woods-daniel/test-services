#!/bin/bash

if [ ! -f $1 ]
then
	echo "$1 does not exist... Exiting..."
	exit
fi

if [ ! -f snippet.xml ]
then 
	echo "snippet.xml does not exist. No changes needed."
	exit
fi

match="</web-app>"
snippet=`cat -E snippet.xml`
snippet="$snippet  $match"
webxml=`cat -E $1`

replace=`echo $webxml | sed -r "s/<\/web-app>//g"`

finalXml=`echo "$replace $snippet"`

finalXml=${finalXml//$/'\n'}

echo -e $finalXml > $1
