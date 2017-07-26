#!/usr/bin/env bash
path=$1;
start=$2;
end=$3;

rm -rf $path/grouper*/

for index in `seq $start $end`
do 
	deployPath="${path}/grouper${index}"
	echo "Deploying grouper${index} in ${deployPath} with database grouper${index}."
	mkdir $deployPath
	cp -rf * $deployPath
	sed -i "s/grouper/grouper${index}/g" "${deployPath}/WEB-INF/config.properties"
	rm "${deployPath}/*.sh"
	rm "${deployPath}/*.sql"
done