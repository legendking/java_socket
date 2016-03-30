if [ ! -d bin ]; then 
	mkdir bin 
fi
find -name \*.java >javaFiles.txt
javac -d bin @javaFiles.txt
rm javaFiles.txt