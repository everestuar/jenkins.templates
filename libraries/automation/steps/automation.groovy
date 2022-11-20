void call(Map params){
    def version = params.version
    def environment = params.environment ?: 'sandbox'
    def namespace = params.namespace ?: 'develop'
    def payload = params.payload ?: ''

    def repo = ""

    withFolderProperties{
        repo = env.REPO
    }

    def repo_name = repo.split('/').last()
    def named_profile = "to_be_deleted_jenkins"

    node("built-in") {
        try {
            stage('Print parameters') {
                sh "echo ${repo}"
                sh "echo ${repo_name}"
                sh "echo ${namespace}"
                sh "echo ${payload}"

                // dir("LocalRepository") {
                //     sh "ls"
                //     sh "git remote set-url --push origin codecommit::us-west-2://${repo_name}"
                //     sh "git fetch -p origin"
                //     sh "git push --mirror --follow-tags"
                // }

                // sh "rm -rf LocalRepository"
            }

        } catch (err) {
            echo "${err}"
        }
    }

}