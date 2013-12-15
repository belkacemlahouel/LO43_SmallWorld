# Compiling and directly executing Java Programs  with Terminal ; just type ./make and the program will be compiled + executed
# (.class files stored in ./class directory)

echo "Cleaning ./class directory"
rm ./class/*
echo "Compiling Application.java"
javac Application.java -d class
cd class
echo "Executing class/Application"
java Application
