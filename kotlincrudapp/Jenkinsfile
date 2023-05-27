pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Hello World'
                sh """
                ls
                pwd
                date
                git clone https://github.com/anshulgupta123/kotlinspringbootcrud.git
                ls
                pwd
                cd kotlinspringbootcrud/kotlincrudapp
                mvn clean install
                """
            }
        }
    }
}
