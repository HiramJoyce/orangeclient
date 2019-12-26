#!/usr/bin/expect -f
set TARGET [lindex $argv 0]
set USER [lindex $argv 1]
set PASSWD [lindex $argv 2]
set PORT [lindex $argv 3]
set MANAGER_HOST [lindex $argv 4]
set timeout 10

spawn ssh $USER@$TARGET -p $PORT 

expect {
    "*yes/no" {send "yes\r"; exp_continue}
    "*password:" {send "$PASSWD\r"}
}

#\r 代表回车执行

send "cd ~\r"
send "mkdir orange\r"
send "cd orange\r"
send "wget http://$MANAGER_HOST:8080/server.py\r"
send "ls\r"
send "python server.py & \r"
interact