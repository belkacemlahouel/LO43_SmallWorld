# Compiling Java Programs  with Terminal ; just type ./make and the program will be compiled + executed + cleaning (rm *~)

echo "Compiling Application.java"
javac Application.java -d class
cd class
echo "Executing class/Application"
java Application
