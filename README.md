-> ./mvnw mn:run -P local   in loc de mn:run -P local

-> Deci ca sa mearga trebuie sa schimbi versiunea java la una 11

export JAVA_HOME=/usr/lib/jvm/jdk-11.0.12
export PATH=$JAVA_HOME/bin:$PATH

sau
export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_301
export PATH=$JAVA_HOME/bin:$PATH

sau Ca sa verifici ce ai faci
sudo update-alternatives --config java
java -version
->ASTA IN TERMINAL. APOI:
./mvnw clean mn:run -P local


-> se creaza proiect number-producer:
mn create-app com.sd.laborator.number-producer --lang kotlin --build maven

->se creaza functie serverless number-consumer:
mn create-function-app com.sd.laborator.number-consumer --lang kotlin --build maven
->grija ca trebuie si configurari ca sa poti folosi rabbit:fisier in resources application.yml

-> in esenta nu a mers, macar ia din lab implementarea profului daca nu...