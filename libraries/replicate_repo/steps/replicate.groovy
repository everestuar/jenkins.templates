
void call(Map params){
    def version = params.version
    def environment = params.environment ?: 'sandbox'
    def namespace = params.namespace ?: 'develop'

    def repo = ""

    withFolderProperties{
        repo = env.REPO
    }

    def repo_name = repo.split('/').last()

    node("built-in") {
        stage('Replicate to second region') {
            sh "echo ${repo}"
            sh "echo ${repo_name}"
            sh "pwd"
            sh "rm -rf LocalRepository"
            sh "git clone --mirror codecommit::us-east-2://${repo_name} LocalRepository"
            sh "pwd"
            sh "ls -ltr"

            dir("LocalRepository"){
                sh "ls"
                sh "git remote set-url --push origin codecommit::us-west-2://${repo_name}"
                sh "git fetch -p origin"
                sh "git push --mirror --follow-tags"
            }
        }

        stage("Replicate to second account") {
            sh "echo ${repo}"
            sh "echo ${repo_name}"
            sh "pwd"
        }
    }

}