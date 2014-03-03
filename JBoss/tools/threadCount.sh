#!/bin/sh
echo Red Hat Global Support Service
echo

s_time=60
c_user="admin"
c_pass="admin"
b_ip="10.66.129.69"

while true
do
echo "$(date)"
echo JVM
./twiddle.sh -s $b_ip:1099 -u $c_user -p $c_pass get "java.lang:type=Threading" ThreadCount
echo 8009
./twiddle.sh -s $b_ip:1099 -u $c_user -p $c_pass get "jboss.web:name=ajp-$b_ip-8009,type=ThreadPool" currentThreadsBusy
echo 8080
./twiddle.sh -s $b_ip:1099 -u $c_user -p $c_pass get "jboss.web:name=http-$b_ip-8080,type=ThreadPool" currentThreadsBusy
echo
sleep $s_time
done
