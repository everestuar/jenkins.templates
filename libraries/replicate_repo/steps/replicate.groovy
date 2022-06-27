
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
            sh "git clone --mirror codecommit::us-east-2://${repo_name} LocalRepository"
            
            dir("/LocalRepository"){
                sh "ls"
            }


        }
    }

}