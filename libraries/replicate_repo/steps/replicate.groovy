
void call(Map params){
    def version = params.version
    def environment = params.environment ?: 'sandbox'
    def namespace = params.namespace ?: 'develop'

    def repo = ""

    withFolderProperties{
        repo = env.REPO
    }

    def app_name = repo.split('/').last()

    node("built-in") {
        stage('Replicate to second region') {
            sh "echo ${repo}"
        }
    }

}