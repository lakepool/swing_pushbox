#/bin/sh
#java -jar pushbox.jar
if [ $# = "1" ] && [ $1 = "--clean" ]; 
	then
		rm -r pushbox
		exit
	fi

cat README.txt
javac -d . src/*.java
cp -r img ./pushbox
java pushbox.game