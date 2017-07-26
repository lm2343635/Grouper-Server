#!/usr/bin/env bash

username=$1;
password=$2;
start=$3;
end=$4;

for index in `seq $start $end`
do
    echo "Creating database grouper${index}."
	echo "drop database if exists grouper${index}; create database grouper${index} character set utf8 collate utf8_general_ci; use grouper${index}; source `pwd`/grouper.sql;" | mysql -u $username -p$password
done